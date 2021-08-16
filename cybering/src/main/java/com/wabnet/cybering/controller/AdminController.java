package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final AdminRepository adminRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;

    public AdminController(AdminRepository adminRepository, AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository) {
        this.adminRepository = adminRepository;
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
    }

    @PostMapping(value = "/admin/cybering", headers = "action=admin-check")
    public SimpleString checkAdmin(@RequestHeader HttpHeaders httpHeaders) {
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tAttempt to visit admin page without set cookie");
            return new SimpleString("no");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records in admin page");
            return new SimpleString("no");
        }
        Optional<Admins> admins = this.adminRepository.findById(token.getEmail());
        if ( admins.isEmpty() ) {
            System.out.println("\tAttempt to visit admin page from: " + token.getEmail());
            return new SimpleString("no");
        }
        return new SimpleString("ok");
    }

    @PostMapping(value = "/admin/cybering", headers = "action=admin-list")
    public Professional[] list(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tAdmin request: list");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tAttempt to visit admin page without set cookie");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records in admin page");
            return null;
        }
        Optional<Admins> admins = this.adminRepository.findById(token.getEmail());
        if ( admins.isEmpty() ) {
            System.out.println("\tAttempt to visit admin page from: " + token.getEmail());
            return null;
        }

        List<Professional> professionals = this.professionalRepository.findAll();
        return professionals.toArray(new Professional[0]);
    }
}
