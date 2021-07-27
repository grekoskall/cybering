package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PasswordResetController {
    private final ProfessionalRepository professionalRepository;

    public PasswordResetController(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @PostMapping("/forgotpassword")
    public SimpleString getEmail(@RequestBody SimpleString email) {

        if( professionalRepository.findByEmail(email.getData()).isEmpty() ) {
            System.out.println("sahfas");
            return new SimpleString("failed");
        }
        System.out.println("okokok");
        return new SimpleString("success");
    }
}
