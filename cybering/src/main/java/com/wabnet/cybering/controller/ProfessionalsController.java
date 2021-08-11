package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.info.PersonalInfoList;
import com.wabnet.cybering.model.signin.info.SignInfo;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
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
@CrossOrigin(origins = "http://localhost:4200")
public class ProfessionalsController {

    private final ProfessionalRepository professionalRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ConnectionRepository connectionRepository;

    public ProfessionalsController(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository, ConnectionRepository connectionRepository) {
        this.professionalRepository = professionalRepository;
        this.authenticationRepository = authenticationRepository;
        this.connectionRepository = connectionRepository;
    }

    @PostMapping("/")
    public SimpleString singIn(@RequestBody SignInfo signInfo) {
        System.out.println("Sign In request from " + signInfo.getEmail());
        Optional<Professional> professional = professionalRepository.findByEmail(signInfo.getEmail());
        if ( professional.isEmpty() ) {
            System.out.println("\tEmail doesn't exist " + signInfo.getEmail());
            return new SimpleString("failed");
        }

        if ( !professional.get().getPassword().equals(signInfo.getPassword())) {
            System.out.println("\tUser " + signInfo.getEmail() + " provided wrong password");
            return new SimpleString("failed");
        }

        Authentication token = this.authenticationRepository.findByEmail(signInfo.getEmail());
        if ( token == null ) {
            System.out.println("\tThis email doesn't have a token associated with it, but it exists in database, probably check data tables");
            return new SimpleString("failed");
        }

        System.out.println("\tNew Sign in from " + signInfo.getEmail());
        return new SimpleString(token.getToken());
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
            System.out.println("\tThe cookie doesn't match the records");
            return new PersonalInfoList();
        }
        Optional<Professional> professional = this.professionalRepository.findByEmail(token.getEmail());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe email in authRep doesn't belong to a professional yet: " + token.getEmail());
            return new PersonalInfoList();
        }
        String image_url;
        if ( professional.get().getPhoto().equals("default") ) {
            image_url = "default";
        } else {
            try {
                image_url = new String(Files.readAllBytes(Paths.get(professional.get().getPhoto())));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("\tError reading image: " + professional.get().getPhoto() );
                return new PersonalInfoList();
            }
        }
        System.out.println("\tSending profile-info list.");
        return new PersonalInfoList(professional.get().getFirstName(), professional.get().getLastName(),
                professional.get().getPhone(), image_url, professional.get().getBio(),
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
        Optional<Professional> professional = this.professionalRepository.findByEmail(token.getEmail());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe email in authRep doesn't belong to a professional yet: " + token.getEmail());
            return null;
        }

        Optional<Connections> connections = connectionRepository.findById(professional.get().getEmail());
        if ( connections.isEmpty() ) {
            System.out.println("\tThis user doesn't have any connections: " + token.getEmail());
            return null;
        }

        LinkedList<String> con_list = connections.get().getList();
        LinkedList<String[]> con_array = new LinkedList<>();
        for (String email:
             con_list) {
            Optional<Professional> professional1 = professionalRepository.findByEmail(email);
            if ( professional1.isEmpty() ) {
                System.out.println("\tEmail found that doesn't belong to a professional: " + token.getEmail() + "| " + email);
                continue;
            }
            LinkedList<String> infos = new LinkedList<>();
            String image_url;
            if ( professional1.get().getPhoto().equals("default") ) {
                image_url = "default";
            } else {
                try {
                    image_url = new String(Files.readAllBytes(Paths.get(professional1.get().getPhoto())));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("\tError reading image: " + professional1.get().getPhoto() );
                    return null;
                }
            }
            infos.add(professional1.get().getFirstName());
            infos.add(professional1.get().getLastName());
            infos.add(professional1.get().getFirstName() + "_" + professional1.get().getLastName());
            infos.add(image_url);
            con_array.add(infos.toArray(new String[0]));
        }
        
        System.out.println("\tReturning network list.");
        return con_array.toArray(new String[con_array.size()][]);
    }
}
