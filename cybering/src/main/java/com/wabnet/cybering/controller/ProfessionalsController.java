package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.info.SignInfo;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProfessionalsController {

    private final ProfessionalRepository professionalRepository;
    private final AuthenticationRepository authenticationRepository;

    public ProfessionalsController(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository) {
        this.professionalRepository = professionalRepository;
        this.authenticationRepository = authenticationRepository;
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


}
