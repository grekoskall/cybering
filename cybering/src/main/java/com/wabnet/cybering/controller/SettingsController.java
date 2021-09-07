package com.wabnet.cybering.controller;

import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
}
