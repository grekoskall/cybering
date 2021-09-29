package com.wabnet.cybering.controller;


import com.wabnet.cybering.model.articles.*;
import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.notifications.NotificationInfo;
import com.wabnet.cybering.model.notifications.NotificationType;
import com.wabnet.cybering.model.notifications.Notifications;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.notifications.NotificationsRepository;
import com.wabnet.cybering.repository.posts.ArticlesRepository;
import com.wabnet.cybering.repository.posts.CommentsRepository;
import com.wabnet.cybering.repository.posts.LikesRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.DateComparator;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    private final AuthenticationRepository authenticationRepository;
    private final ArticlesRepository articlesRepository;
    private final ProfessionalRepository professionalRepository;
    private final ConnectionRepository connectionRepository;
    private final LikesRepository likesRepository;
    private final CommentsRepository commentsRepository;
    private final NotificationsRepository notificationsRepository;

    public ArticleController(AuthenticationRepository authenticationRepository, ArticlesRepository articlesRepository, ProfessionalRepository professionalRepository, ConnectionRepository connectionRepository, LikesRepository likesRepository, CommentsRepository commentsRepository, NotificationsRepository notificationsRepository) {
        this.authenticationRepository = authenticationRepository;
        this.articlesRepository = articlesRepository;
        this.professionalRepository = professionalRepository;
        this.connectionRepository = connectionRepository;
        this.likesRepository = likesRepository;
        this.commentsRepository = commentsRepository;
        this.notificationsRepository = notificationsRepository;
    }


    @PostMapping(value="/cybering/home-page", headers = "action=article-list")
    public Object[] articleList(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to article list.");
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
        Optional<Connections> connections = connectionRepository.findById(professional.get().getId());

        // Find articles the user posted
        List<Article> articleList =  articlesRepository.findAllByProfid(token.getProfid());
        // Find articles the user's connections posted and merge
        if ( connections.isEmpty() ) {
            System.out.println("\tThis user doesn't have any connections: " + token.getProfid());
        } else {
            for (String connection_profid: connections.get().getList() ) {
                articleList.addAll(
                        articlesRepository.findAllByProfid(connection_profid)
                );
            }
        }
        // articles of non-connections that connections have shown interest
        if ( connections.isEmpty() ) {
            System.out.println("\tThis user doesn't have any connections: " + token.getProfid());
        } else {
            for (String connection_profid: connections.get().getList() ) {
                Optional<Likes> articles = likesRepository.findById(connection_profid);
                if ( articles.isEmpty() ) {
                    continue;
                }
                LinkedList<String> art_ids = articles.get().getArticle_ids();

                for (String id: art_ids ) {
                    Optional<Article> art = articlesRepository.findById(id);
                    if ( art.isEmpty() ) {
                        continue;
                    }
                    int flag = 0;
                    for (Article aaa :
                            articleList) {
                        if (aaa.getId().equals(art.get().getId())) {
                            flag = 1;
                            break;
                        }
                    }
                    if ( articleList.contains(art.get()) || flag == 1  ) {
                        continue;
                    }
                    articleList.add(art.get());
                }
            }
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


    @PostMapping(value = "/cybering/home-page", headers = "action=reply-article")
    public SimpleString replyArticle(@RequestHeader HttpHeaders httpHeaders, @RequestBody ArticleReply articleReply) {
        System.out.println("Got a request to comment to an article: " + articleReply.getAid() + " | " + articleReply.getReply());
        if ( articleReply.getAid() == null || articleReply.getReply() == null ) {
            return new SimpleString("failed");
        }
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return new SimpleString("failed");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new SimpleString("failed");
        }
        Optional<Article> article = this.articlesRepository.findById(articleReply.getAid());
        if ( article.isEmpty() ) {
            System.out.println("\tThe article ID doesn't exist in the records: " + articleReply.getAid());
            return new SimpleString("failed");
        }

        if ( articleReply.getReply().replaceAll("[ \n\t]", "").length() == 0 ) {
            System.out.println("\tThe reply given is empty: " + articleReply.getReply());
            return new SimpleString("failed");
        }


        String[][] comms = article.get().getComments();
        LinkedList<String[]> strings = new LinkedList<>(Arrays.stream(comms).toList());

        strings.add(new String[] {professional.get().getId(), articleReply.getReply()});
        article.get().setComments(strings.toArray(new String[0][]));
        articlesRepository.save(article.get());

        Optional<Comments> comments = this.commentsRepository.findById(professional.get().getId());
        if ( comments.isEmpty() ) {
            LinkedList<String> aids = new LinkedList<>();
            aids.push(article.get().getId());
            this.commentsRepository.save(
                    new Comments(
                            professional.get().getId(),
                            aids
                    )
            );
        } else {
            LinkedList<String> aids = comments.get().getArticle_ids();
            boolean flag = false;
            for (String aid :
                    aids) {
                if(aid.equals(article.get().getId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                aids.push(article.get().getId());
            }
            comments.get().setArticle_ids(aids);
            this.commentsRepository.save(comments.get());
        }
        // Create appropriate notification
        Optional<Professional> articleOwner = professionalRepository.findById(article.get().getProfid());
        if (articleOwner.isPresent()) {
            Optional<Notifications> profilesNotifications = notificationsRepository.findById(articleOwner.get().getId());
            String textPreview = article.get().getText().substring(0, Math.min(article.get().getText().length(), 15));
            if (article.get().getText().length() > 15) {
                textPreview += "...";
            }
            String fullName = professional.get().getFirstName() + " " + professional.get().getLastName();
            NotificationInfo newNotification = new NotificationInfo(professional.get().getId(), fullName, NotificationType.COMMENT, " '" + textPreview + "' from " + article.get().getTimestamp().toString());
            profilesNotifications.get().getNotificationsList().addFirst(newNotification);
            notificationsRepository.save(profilesNotifications.get());
        }
        return new SimpleString("success");
    }

    @PostMapping(value = "/cybering/home-page", headers = "action=interest-article")
    public SimpleString interestArticle(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString simpleString) {
        System.out.println("Got a request to change interest to an article: " + simpleString.getData());
        if ( simpleString.getData() == null) {
            return new SimpleString("failed");
        }
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return new SimpleString("failed");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new SimpleString("failed");
        }
        Optional<Article> article = this.articlesRepository.findById(simpleString.getData());
        if ( article.isEmpty() ) {
            System.out.println("\tThe article ID doesn't exist in the records: " + simpleString.getData());
            return new SimpleString("failed");
        }

        LinkedList<String> likes = new LinkedList<>(Arrays.stream(article.get().getLikes()).toList());

        if ( likes.removeIf(profid -> profid.equals(professional.get().getId())) ) {
            Optional<Likes> likesOptional = this.likesRepository.findById(professional.get().getId());
            if ( likesOptional.isEmpty()) {
                System.out.println("\tNo likes table found for the email!");
                return new SimpleString("failed");
            }
            LinkedList<String> articleIds = likesOptional.get().getArticle_ids();
            if ( !articleIds.removeIf(id -> id.equals(simpleString.getData()) ) ) {
                System.out.println("\tThe article wasn't found in the likes table!");
                return new SimpleString("failed");
            }
            this.likesRepository.save(likesOptional.get());
            System.out.println("\tChanged article from liked to unliked");
        } else {
            likes.add(professional.get().getId());
            Optional<Likes> optionalLikes = this.likesRepository.findById(professional.get().getId());
            if ( optionalLikes.isEmpty() )  {
                System.out.println("\tNo likes table found for the email!");
                return new SimpleString("failed");
            }
            LinkedList<String> articleIds = optionalLikes.get().getArticle_ids();
            articleIds.add(simpleString.getData());
            this.likesRepository.save(optionalLikes.get());
            System.out.println("\tChanged article from unliked to liked");
            // Create appropriate notification
            Optional<Professional> articleOwner = professionalRepository.findById(article.get().getProfid());
            if (articleOwner.isPresent()) {
                Optional<Notifications> profilesNotifications = notificationsRepository.findById(articleOwner.get().getId());
                String textPreview = article.get().getText().substring(0, Math.min(article.get().getText().length(), 15));
                if (article.get().getText().length() > 15) {
                    textPreview += "...";
                }
                String fullName = professional.get().getFirstName() + " " + professional.get().getLastName();
                NotificationInfo newNotification = new NotificationInfo(professional.get().getId(), fullName, NotificationType.LIKE, " '" + textPreview + "' from " + article.get().getTimestamp().toString());
                profilesNotifications.get().getNotificationsList().addFirst(newNotification);
                notificationsRepository.save(profilesNotifications.get());
            }
        }
        article.get().setLikes(likes.toArray(new String[0]));
        this.articlesRepository.save(article.get());
        return new SimpleString("success");
    }

    @PostMapping(value = "/cybering/home-page", headers = "action=post-article")
    public SimpleString postArticle(@RequestHeader HttpHeaders httpHeaders, @RequestBody ArticlePost articlePost) {
        System.out.println("Got a request to post an article: " + articlePost.toString());
        if ( articlePost.getArticleText() == null ) {
            return new SimpleString("failed");
        }
        if ( articlePost.getArticleText().replaceAll("[ \n\t]", "").equals("") ) {
            System.out.println("\tGiven post text is empty");
        }
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return new SimpleString("failed");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new SimpleString("failed");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        String[] categories = articlePost.getArticleCategories().split(" ");
        String image;
        if ( professional.get().getPhoto().equals("default") ) {
            image = "dpp.jpg";
        } else {
            image = professional.get().getPhoto();
        }

        this.articlesRepository.save(
                new Article(
                        professional.get().getWorkPosition() + " at " + professional.get().getWorkPlace(),
                        professional.get().getId(),
                        image,
                        dateTimeFormatter.format(localDateTime),
                        categories,
                        articlePost.getArticleText(),
                        new String[0][],
                        new String[0],
                        new String[][] {new String[] {articlePost.getArticleMedia().toLowerCase(), articlePost.getArticleUrl()}}
                )
        );
        return new SimpleString("success");
    }


}
