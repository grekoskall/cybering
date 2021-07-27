package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.signin.info.SignInfo;
import com.wabnet.cybering.model.signin.tokens.AuthToken;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.ValidationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProfessionalsController {

    private final ProfessionalRepository professionalRepository;
    private final ValidationRepository validationRepository;

    public ProfessionalsController(ProfessionalRepository professionalRepository, ValidationRepository validationRepository) {
        this.professionalRepository = professionalRepository;
        this.validationRepository = validationRepository;
    }

    @GetMapping("/professionals")
    public List<Professional> getProfessionals() {
        return professionalRepository.findAll();
    }

    @PostMapping("/professionals")
    public void addProfessional(@RequestBody Professional professional) {
        professionalRepository.save(professional);
    }

    @PostMapping("/")
    public AuthToken singIn(@RequestBody SignInfo signInfo) {
        System.out.println("got a request" + signInfo.getEmail());
        if ( professionalRepository.findByEmail(signInfo.getEmail()).isEmpty() ) {
            return new AuthToken("failed");
        }
        return new AuthToken("whatthef3uck");
    }


}
