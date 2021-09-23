package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.ProfessionalProfile.ProfessionalProfileInfo;
import com.wabnet.cybering.model.ProfessionalProfile.UserStatus;
import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.privacy.Privacy;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.notifications.ConnectionRequestsRepository;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileViewController {
    private final AuthenticationRepository authenticationRepository;
    private final AdminRepository adminRepository;
    private final ProfessionalRepository professionalRepository;
    private final ConnectionRepository connectionRepository;
    private final ConnectionRequestsRepository connectionRequestsRepository;


    public ProfileViewController(AuthenticationRepository authenticationRepository, AdminRepository adminRepository, ProfessionalRepository professionalRepository, ConnectionRepository connectionRepository, ConnectionRequestsRepository connectionRequestsRepository) {
        this.authenticationRepository = authenticationRepository;
        this.adminRepository = adminRepository;
        this.professionalRepository = professionalRepository;
        this.connectionRepository = connectionRepository;
        this.connectionRequestsRepository = connectionRequestsRepository;
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

        if (connectionRequestsRepository.hasSentRequestToProfile(professional.get().getId(), profidFromUrl.getData())) {
            // User has cancelled his request
            connectionRequestsRepository.deleteConnectionRequest(professional.get().getId(), profidFromUrl.getData());
        } else if (connectionRequestsRepository.hasReceivedRequestFromProfile(professional.get().getId(), profidFromUrl.getData())) {
            // User has approved request from the profile
            if (connectionRepository.createUsersConnection(professional.get().getId(), profidFromUrl.getData())) {
                connectionRequestsRepository.deleteConnectionRequest(profidFromUrl.getData(), professional.get().getId());
            }
            else {
                return new SimpleString("failed");
            }
        } else {
            // User sent request to profile
            connectionRequestsRepository.createConnectionRequest(professional.get().getId(), profidFromUrl.getData());
        }

        return new SimpleString("success");
    }

}
