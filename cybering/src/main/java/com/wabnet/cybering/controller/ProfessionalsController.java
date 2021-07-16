package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProfessionalsController {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalsController(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @GetMapping("/professionals")
    public List<Professional> getProfessionals() {
        return professionalRepository.findAll();
    }

    @PostMapping("/professionals")
    public void addProfessional(@RequestBody Professional professional) {
        professionalRepository.save(professional);
    }
}
