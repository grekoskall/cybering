package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.personalinfo.PersonalInfo;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonalInfoController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;

    public PersonalInfoController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
    }

    @PostMapping(value="/cybering/personalinfo", headers = "action=personal-info-get")
    public PersonalInfo getPersonalInfo(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to get Personal info.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        PersonalInfo personalInfo = new PersonalInfo(professional.get());

        System.out.println("\tReturning Personal info...");
        return personalInfo;
    }

    @PostMapping(value="/cybering/personalinfo", headers = "action=personal-info-set")
    public SimpleString setPersonalInfo(@RequestHeader HttpHeaders httpHeaders, @RequestBody PersonalInfo newPersonalInfo) {
        System.out.println("\tGot a request to set Personal info.");
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

        updateProfessionalsPersonalInfo(professional.get(), newPersonalInfo);

        return new SimpleString("success");
    }

    private void updateProfessionalsPersonalInfo(Professional _professional, PersonalInfo _personalInfo) {
        _professional.setWorkPosition(_personalInfo.getWorkPosition());
        _professional.setWorkPlace(_personalInfo.getWorkPlace());
        _professional.setBio(_personalInfo.getBio());
        _professional.setWorkExperience(_personalInfo.getWorkExperience());
        _professional.setEducation(_personalInfo.getEducation());
        _professional.setSkills(_personalInfo.getSkills());
        _professional.setPrivacySettings(_personalInfo.getPrivacySettings());

        this.professionalRepository.save(_professional);
    }
}
