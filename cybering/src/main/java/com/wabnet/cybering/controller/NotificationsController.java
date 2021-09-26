package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.notifications.Notifications;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.notifications.NotificationsRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationsController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
    private final NotificationsRepository notificationsRepository;

    public NotificationsController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, NotificationsRepository notificationsRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @PostMapping(value="/cybering/notifications", headers = "action=notifications-get")
    public Notifications getNotifications(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to get Notifications.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return null;
            //return new SimpleString("failed");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
            //return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
            //return new SimpleString("failed");
        }
        Optional<Notifications> profNotifications = this.notificationsRepository.findById(professional.get().getId());
        if (profNotifications.isEmpty()) {
            System.out.println("\tNo notifications record found for this Professional");
            return null;
        }

        System.out.println("\tReturning user notifications...");
        return profNotifications.get();
    }
}
