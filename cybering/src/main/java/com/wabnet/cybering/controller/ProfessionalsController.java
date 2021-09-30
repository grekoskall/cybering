package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.signin.info.PersonalInfoList;
import com.wabnet.cybering.model.signin.info.SignInfo;
import com.wabnet.cybering.model.signin.response.SignInResponse;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class ProfessionalsController {

    private final ProfessionalRepository professionalRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ConnectionRepository connectionRepository;
    private final AdminRepository adminRepository;

    public ProfessionalsController(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository, ConnectionRepository connectionRepository, AdminRepository adminRepository) {
        this.professionalRepository = professionalRepository;
        this.authenticationRepository = authenticationRepository;
        this.connectionRepository = connectionRepository;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/")
    public SignInResponse singIn(@RequestBody SignInfo signInfo) {
        System.out.println("Sign In request from " + signInfo.getEmail());
        Optional<Professional> professional = professionalRepository.findByEmail(signInfo.getEmail());
        Optional<Admins> admins = adminRepository.findById(signInfo.getEmail());
        if (professional.isEmpty() && admins.isEmpty()) {
            System.out.println("\tEmail doesn't exist " + signInfo.getEmail());
            return new SignInResponse("failed", "failed");
        }
        Authentication token;
        if (professional.isPresent())
            token = this.authenticationRepository.findByProfid(professional.get().getId());
        else
            token = authenticationRepository.findByProfid(signInfo.getEmail());
        if (token == null) {
            System.out.println("\tThis email doesn't have a token associated with it, but it exists in database, probably check data tables");
            return new SignInResponse("failed", "failed");
        }
        if (professional.isPresent()) {
            if (!professional.get().getPassword().equals(signInfo.getPassword())) {
                System.out.println("\tUser " + signInfo.getEmail() + " provided wrong password");
                return new SignInResponse("failed", "failed");
            }
            System.out.println("\tNew Sign in from " + signInfo.getEmail());
            return new SignInResponse(token.getToken(), "professional");
        }
        if (!admins.get().getPassword().equals(signInfo.getPassword())) {
            System.out.println("\tUser " + signInfo.getEmail() + " provided wrong password");
            return new SignInResponse("failed", "failed");
        }
        System.out.println("\tNew Sign in from " + signInfo.getEmail());
        return new SignInResponse(token.getToken(), "admin");
    }

    @PostMapping(value = "/cybering/home-page", headers = "action=profile-info-list")
    public PersonalInfoList infoList(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request for profile-info list.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return new PersonalInfoList();
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records: " + cookie);
            return new PersonalInfoList();
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new PersonalInfoList();
        }

        System.out.println("\tSending profile-info list.");
        return new PersonalInfoList(professional.get().getFirstName(), professional.get().getLastName(),
                professional.get().getPhone(), professional.get().getPhoto(), professional.get().getBio(),
                professional.get().getWorkPosition(), professional.get().getWorkPlace(),
                professional.get().getSkills());
    }

    @PostMapping(value = "/cybering/home-page", headers = "action=network-list")
    public String[][] networkList(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request for network-info list.");
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
            System.out.println("\tThe id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        Optional<Connections> connections = connectionRepository.findById(professional.get().getId());
        if ( connections.isEmpty() ) {
            System.out.println("\tThis user doesn't have any connections: " + token.getProfid());
            return null;
        }

        LinkedList<String> con_list = connections.get().getList();
        LinkedList<String[]> con_array = new LinkedList<>();
        for (String profid:
             con_list) {
            Optional<Professional> professional1 = professionalRepository.findById(profid);
            if ( professional1.isEmpty() ) {
                System.out.println("\tId found that doesn't belong to a professional: " + token.getProfid() + "| " + profid);
                continue;
            }
            LinkedList<String> infos = new LinkedList<>();

            infos.add(professional1.get().getFirstName());
            infos.add(professional1.get().getLastName());
            infos.add(professional1.get().getId());
            infos.add(professional1.get().getPhoto());
            con_array.add(infos.toArray(new String[0]));
        }
        
        System.out.println("\tReturning network list.");
        return con_array.toArray(new String[con_array.size()][]);
    }
}
