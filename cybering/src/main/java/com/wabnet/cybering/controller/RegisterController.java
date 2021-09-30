package com.wabnet.cybering.controller;


import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.AuthTokenMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class RegisterController {
    private final ProfessionalRepository professionalRepository;
    private final AuthenticationRepository authenticationRepository;
    @Autowired
    private final AuthTokenMaker authTokenMaker;

    public RegisterController(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository, AuthTokenMaker authTokenMaker) {
        this.professionalRepository = professionalRepository;
        this.authenticationRepository = authenticationRepository;
        this.authTokenMaker = authTokenMaker;
    }

    @PostMapping("/register")
    public SimpleString register(@RequestBody SimpleString simpleString) {
        System.out.println("Register request from: " + simpleString.getData());
        if (professionalRepository.findByEmail(simpleString.getData()).isPresent()) {
            System.out.println("\tFound a user with the same email: " + simpleString.getData());
            return new SimpleString("used");
        }

        String token = authTokenMaker.makeToken(simpleString.getData());
        if ( authenticationRepository.existsById(token) ) {
            token = token.replaceAll(token.substring(2,5), token.substring(1,4));
        }

        Professional newProfessional = new Professional(simpleString.getData());
        newProfessional = professionalRepository.save(newProfessional);

        authenticationRepository.save(new Authentication(token, newProfessional.getId(), false));
        return new SimpleString(token);
    }
}

