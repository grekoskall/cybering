package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.ProfessionalProfile.ProfessionalProfileInfo;
import com.wabnet.cybering.model.ProfessionalProfile.UserStatus;
import com.wabnet.cybering.model.articles.Article;
import com.wabnet.cybering.model.articles.ArticleResponse;
import com.wabnet.cybering.model.articles.Likes;
import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.network.Network;
import com.wabnet.cybering.model.notifications.NotificationInfo;
import com.wabnet.cybering.model.notifications.NotificationType;
import com.wabnet.cybering.model.notifications.Notifications;
import com.wabnet.cybering.model.privacy.Privacy;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.notifications.ConnectionRequestsRepository;
import com.wabnet.cybering.repository.notifications.NotificationsRepository;
import com.wabnet.cybering.repository.posts.ArticlesRepository;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.DateComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileViewController {
    private final AuthenticationRepository authenticationRepository;
    private final AdminRepository adminRepository;
    private final ProfessionalRepository professionalRepository;
    private final ConnectionRepository connectionRepository;
    private final ConnectionRequestsRepository connectionRequestsRepository;
    private final NotificationsRepository notificationsRepository;
    private final ArticlesRepository articlesRepository;


    public ProfileViewController(AuthenticationRepository authenticationRepository, AdminRepository adminRepository, ProfessionalRepository professionalRepository, ConnectionRepository connectionRepository, ConnectionRequestsRepository connectionRequestsRepository, NotificationsRepository notificationsRepository, ArticlesRepository articlesRepository) {
        this.authenticationRepository = authenticationRepository;
        this.adminRepository = adminRepository;
        this.professionalRepository = professionalRepository;
        this.connectionRepository = connectionRepository;
        this.connectionRequestsRepository = connectionRequestsRepository;
        this.notificationsRepository = notificationsRepository;
        this.articlesRepository = articlesRepository;
    }

    @PostMapping(value="/cybering/profile", headers = "action=professional-info-get")
    public ProfessionalProfileInfo getProfessionalProfileInfo(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString profidFromUrl) {
        System.out.println("\tGot a request to get Professional profile info.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professionalToView = this.professionalRepository.findById(profidFromUrl.getData());
        if (professionalToView.isEmpty()) {
            System.out.println("\tThe url parameter doesn't match a professional");
            return null;
        }

        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        Optional<Admins> admin = this.adminRepository.findById(token.getProfid());
        if (admin.isPresent()) {
            System.out.println("\tReturning professional to admin...");
            return initProfessionalProfileInfo(profidFromUrl, UserStatus.IS_ADMIN);
        } else if (professional.isPresent()){
            System.out.println("\tReturning professional to user...");
            if (professional.get().getId().equals(professionalToView.get().getId())) {
                return initProfessionalProfileInfo(profidFromUrl, UserStatus.SAME_USER);
            }
            if (connectionRepository.areConnected(professional.get().getId(), profidFromUrl.getData())) {
                return initProfessionalProfileInfo(profidFromUrl, UserStatus.CONNECTED);
            } else {
                if (connectionRequestsRepository.hasSentRequestToProfile(professional.get().getId(), profidFromUrl.getData())) {
                    return initProfessionalProfileInfo(profidFromUrl, UserStatus.SENT_REQUEST);
                } else if (connectionRequestsRepository.hasReceivedRequestFromProfile(professional.get().getId(), profidFromUrl.getData())) {
                    return initProfessionalProfileInfo(profidFromUrl, UserStatus.RECEIVED_REQUEST);
                } else {
                    return initProfessionalProfileInfo(profidFromUrl, UserStatus.NOT_CONNECTED);
                }
            }
        } else {
            System.out.println("\tToken doesn't match any record...");
            return null;
        }

    }

    ProfessionalProfileInfo initProfessionalProfileInfo (SimpleString profidToView, UserStatus userStatus) {
        Optional<Professional> professional = this.professionalRepository.findById(profidToView.getData());
        if (professional.isEmpty())
            return null;

        String[] education, workExperience, skills;
        if (professional.get().getPrivacySettings().getEducation().equals(Privacy.PRIVATE))
            education = null;
        else
            education = professional.get().getEducation();
        if (professional.get().getPrivacySettings().getWorkExperience().equals(Privacy.PRIVATE))
            workExperience = null;
        else
            workExperience = professional.get().getWorkExperience();
        if (professional.get().getPrivacySettings().getSkills().equals(Privacy.PRIVATE))
            skills = null;
        else
            skills = professional.get().getSkills();

        return new ProfessionalProfileInfo(
             professional.get().getId(),
             professional.get().getEmail(),
             professional.get().getFirstName(),
             professional.get().getLastName(),
             professional.get().getAge(),
             professional.get().getPhone(),
             professional.get().getPhoto(),
             professional.get().getWorkPosition(),
             professional.get().getWorkPlace(),
             professional.get().getBio(),
             workExperience,
             education,
             skills,
             userStatus
        );

    }


    @PostMapping(value="/cybering/profile", headers = "action=connect-request")
    public SimpleString connectionRequest(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString profidFromUrl) {
        System.out.println("\tGot a request to create a connection.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return new SimpleString("failed");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new SimpleString("failed");
        }

        Optional<Professional> professionalToView = this.professionalRepository.findById(profidFromUrl.getData());
        if (professionalToView.isEmpty()) {
            System.out.println("\tThe url parameter doesn't match a professional");
            return new SimpleString("failed");
        }

        if (connectionRequestsRepository.hasSentRequestToProfile(professional.get().getId(), profidFromUrl.getData())) {
            // User has cancelled his request
            connectionRequestsRepository.deleteConnectionRequest(professional.get().getId(), profidFromUrl.getData());
        } else if (connectionRequestsRepository.hasReceivedRequestFromProfile(professional.get().getId(), profidFromUrl.getData())) {
            // User has approved request from the profile
            if (connectionRepository.createUsersConnection(professional.get().getId(), profidFromUrl.getData())) {
                connectionRequestsRepository.deleteConnectionRequest(profidFromUrl.getData(), professional.get().getId());
                // Create appropriate notification
                Optional<Notifications> profilesNotifications = notificationsRepository.findById(profidFromUrl.getData());
                if (profilesNotifications.isPresent()) {
                    String fullName = professional.get().getFirstName() + " " + professional.get().getLastName();
                    NotificationInfo newNotificationInfo = new NotificationInfo(professional.get().getId(), fullName, NotificationType.CONNECTION_APPROVED, "");
                    profilesNotifications.get().getNotificationsList().addFirst(newNotificationInfo);
                    notificationsRepository.save(profilesNotifications.get());
                }
            }
            else {
                return new SimpleString("failed");
            }
        } else {
            // User sent request to profile
            connectionRequestsRepository.createConnectionRequest(professional.get().getId(), profidFromUrl.getData());
            // Create apropriate notification
            Optional<Notifications> profilesNotifications = notificationsRepository.findById(profidFromUrl.getData());
            if (profilesNotifications.isPresent()) {
                String fullName = professional.get().getFirstName() + " " + professional.get().getLastName();
                NotificationInfo newNotificationInfo = new NotificationInfo(professional.get().getId(), fullName, NotificationType.CONNECTION_REQUEST, "");
                profilesNotifications.get().getNotificationsList().addFirst(newNotificationInfo);
                notificationsRepository.save(profilesNotifications.get());
            }
        }

        return new SimpleString("success");
    }

    @PostMapping(value="/cybering/profile", headers = "action=article-user-list")
    public Object[] articleList(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString profidFromUrl) {
        System.out.println("\tGot a request to User's article list.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        Optional<Professional> professionalToView = this.professionalRepository.findById(profidFromUrl.getData());
        if (professionalToView.isEmpty()) {
            System.out.println("\tThe url parameter doesn't match a professional");
            return null;
        }

        // Find articles the user posted
        List<Article> articleList =  articlesRepository.findAllByProfid(profidFromUrl.getData());

        if (articleList.isEmpty()) {
            return null;
        }

        articleList.sort(new DateComparator());

        List<ArticleResponse> articleResponseList = new LinkedList<>();

        for (Article asas :
                articleList) {
            Optional<Professional> prof = this.professionalRepository.findById(asas.getProfid());
            LinkedList<String> articleLikes = new LinkedList<>();
            for ( String like_profid : asas.getLikes() ) {
                Optional<Professional> prof2 = this.professionalRepository.findById(like_profid);
                prof2.ifPresent(value -> articleLikes.add(
                        value.getFirstName() + " " + value.getLastName()
                ));
            }
            LinkedList<String[]> articleComments = new LinkedList<>();
            for ( String[] comment : asas.getComments() ) {
                Optional<Professional> prof2 = this.professionalRepository.findById(comment[0]);
                prof2.ifPresent(value -> articleComments.add(new String[]{value.getFirstName(), value.getLastName(), comment[1], value.getId()}));
            }
            boolean likes = false;
            for ( String profid: asas.getLikes() ) {
                if ( profid.equals(token.getProfid()) ) {
                    likes = true;
                    break;
                }
            }
            Boolean finalLikes = likes;
            prof.ifPresent(value -> articleResponseList.add(
                    new ArticleResponse(
                            asas.getId(),
                            asas.getTitle(),
                            value.getFirstName(),
                            value.getLastName(),
                            asas.getProfid(),
                            asas.getPhoto_url(),
                            asas.getTimestamp(),
                            asas.getCategories(),
                            asas.getText(),
                            articleComments.toArray(new String[0][]),
                            articleLikes.toArray(new String[0]),
                            asas.getMedia(),
                            finalLikes
                    )
            ));
        }

        System.out.println("\tReturning a article list.");
        return articleResponseList.toArray();
    }

    @PostMapping(value="/cybering/profile", headers = "action=network-user")
    public Network[] getNetwork(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString profidFromUrl) {
        System.out.println("\tGot a request to get User's network.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        Optional<Professional> professionalToView = this.professionalRepository.findById(profidFromUrl.getData());
        if (professionalToView.isEmpty()) {
            System.out.println("\tThe url parameter doesn't match a professional");
            return null;
        }

        Optional<Connections> connections = this.connectionRepository.findById(profidFromUrl.getData());
        if (connections.isEmpty()) {
            System.out.println("\tThis user has no connections");
            return null;
        }

        LinkedList<Network> networkLinkedList = new LinkedList<>();
        LinkedList<String> conList = connections.get().getList();
        for (String profid :
                conList) {
            Optional<Professional> professionalConnection = this.professionalRepository.findById(profid);
            if (professionalConnection.isEmpty()) {
                System.out.println("\tThis user has an unknown connection: " + professional.get());
                return null;
            }
            String image;
            if ( professional.get().getPhoto().equals("default") ) {
                image="dpp.jpg";
            } else {
                image = professional.get().getPhoto();
            }
            networkLinkedList.add(
                    new Network(
                            professionalConnection.get().getFirstName(),
                            professionalConnection.get().getLastName(),
                            professionalConnection.get().getId(),
                            image,
                            professionalConnection.get().getWorkPlace(),
                            professionalConnection.get().getWorkPosition()
                    )
            );
        }

        System.out.println("\tReturning network");
        return networkLinkedList.toArray(new Network[0]);
    }

}
