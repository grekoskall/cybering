package com.wabnet.cybering.controller;


import com.wabnet.cybering.model.signin.info.RegisterInfo;
import com.wabnet.cybering.model.signin.tokens.AuthToken;
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
@CrossOrigin(origins = "http://localhost:4200")
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
    public AuthToken register(@RequestBody RegisterInfo registerInfo) {
        if ( !professionalRepository.findByEmail(registerInfo.getEmail()).isEmpty() ) {
            System.out.println("Found a user with the same email: " + registerInfo.getEmail());
            return new AuthToken("used");
        }

        String token = authTokenMaker.makeToken(
                registerInfo.getEmail(),
                registerInfo.getFirstName(),
                registerInfo.getLastName(),
                registerInfo.getPassword()
        );

        authenticationRepository.save(new Authentication(token, registerInfo.getEmail()));
        professionalRepository.save(new Professional(registerInfo.getFirstName(), registerInfo.getLastName(), 0, registerInfo.getEmail()) );
        return new AuthToken(token);
    }
}

