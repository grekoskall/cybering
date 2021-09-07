package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.discussions.Discussion;
import com.wabnet.cybering.model.settings.ChangePassword;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;

    public SettingsController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
    }

    @PostMapping(value="/cybering/settings", headers = "action=settings-email-get")
    public SimpleString getEmail(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to get Email.");
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

        System.out.println("\tReturning email...");
        return new SimpleString(professional.get().getEmail());
    }

    @PostMapping(value="/cybering/settings", headers = "action=settings-email-set")
    public SimpleString setEmail(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString newEmail) {
        System.out.println("\tGot a request to set Email.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return new SimpleString("invalid");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return new SimpleString("invalid");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new SimpleString("invalid");
        }

        Optional<Professional> professionalEmailCheck = this.professionalRepository.findByEmail(newEmail.getData());
        if (professionalEmailCheck.isPresent()) {
            System.out.println("\tThis Email is already being used.");
            return new SimpleString("exists");
        }

        professional.get().setEmail(newEmail.getData());
        this.professionalRepository.save(professional.get());
        System.out.println("\tSuccessfully changed Email.");
        return new SimpleString("success");
    }

    @PostMapping(value="/cybering/settings", headers = "action=settings-password-set")
    public SimpleString setPassword(@RequestHeader HttpHeaders httpHeaders, @RequestBody ChangePassword newPassword) {
        System.out.println("\tGot a request to set Password.");
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

        if (!professional.get().getPassword().equals(newPassword.getCurrentPassword())) {
            System.out.println("\tCurrent password check failed.");
            return new SimpleString("failed");
        }

        professional.get().setPassword(newPassword.getNewPassword());
        this.professionalRepository.save(professional.get());
        System.out.println("\tSuccessfully changed Password.");
        return new SimpleString("success");
    }
}
