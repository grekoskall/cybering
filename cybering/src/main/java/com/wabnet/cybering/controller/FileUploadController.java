package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.articles.Likes;
import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.info.RegisterInfo;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.posts.LikesRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.AuthTokenMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
    private final LikesRepository likesRepository;
    @Autowired
    private final AuthTokenMaker authTokenMaker;

    public FileUploadController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, LikesRepository likesRepository, AuthTokenMaker authTokenMaker) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
        this.likesRepository = likesRepository;
        this.authTokenMaker = authTokenMaker;
    }

    @PostMapping(value = "/register/add-phone", headers = "action=add-photo")
    public SimpleString addPhoto(
            @RequestHeader  HttpHeaders httpHeaders,
            @RequestParam("file") MultipartFile profilePhoto
    ) {
        System.out.println("Got a request to add a photo");

        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty" );
            return new SimpleString("failed");
        }
        Authentication authentication = this.authenticationRepository.findByToken(cookie);
        if ( authentication == null ) {
            System.out.println("\tToken not in database: " + cookie);
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }

        Optional<Professional> professional = this.professionalRepository.findById(authentication.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tCookie not belonging to anyone: " + cookie);
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }

        String file_path = "C:/tmp/" + "PROFILE_PHOTO" + "_" + cookie.substring(0,5);
        File photo = new File(file_path);
        if ( profilePhoto.getName().equals("dpp.jpg") || profilePhoto.getOriginalFilename() == null) {
            try {
                profilePhoto.transferTo(photo);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("\tError while transferring to file: " + photo.getPath());
                if ( photo.exists() && !photo.delete() ) {
                    System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
                }
                this.authenticationRepository.flushRepository();
                this.professionalRepository.flushRepository();
                return new SimpleString("failed");
            }
        } else {
            try {
                FileWriter writer = new FileWriter(file_path);
                writer.write(new String(profilePhoto.getBytes()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("\tError while writing to file: " + photo.getPath());
                if ( photo.exists() && !photo.delete() ) {
                    System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
                }
                this.authenticationRepository.flushRepository();
                this.professionalRepository.flushRepository();
                return new SimpleString("failed");
            }
        }

        System.out.println("\tRequest to add a photo completed successfully");
        return new SimpleString("ok");
    }

    @PostMapping(value = "/register/add-phone", headers = "action=add-info")
    public SimpleString addInfo(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody RegisterInfo registerInfo
    ) {
        System.out.println("Got a request to add register info");

        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty" );
            return new SimpleString("failed");
        }
        Authentication authentication = this.authenticationRepository.findByToken(cookie);
        if ( authentication == null ) {
            System.out.println("\tToken not in database: " + cookie);
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }

        String file_path = "C:/tmp/" + "PROFILE_PHOTO" + "_" + cookie.substring(0,5);
        File photo = new File(file_path);
        if ( registerInfo.getEmail() == null || registerInfo.getEmail().equals("null")) {
            System.out.println("\tEmail is empty (null)");
            if ( photo.exists() && !photo.delete() ) {
                System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
            }
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findByEmail(registerInfo.getEmail());
        if ( professional.isPresent() ) {
            Optional<Authentication> tempAuthentication = Optional.ofNullable(this.authenticationRepository.findByProfid(professional.get().getId()));
            if (tempAuthentication.isEmpty()) {
                this.authenticationRepository.flushRepository();
                this.professionalRepository.flushRepository();
                if ( photo.exists() && !photo.delete() ) {
                    System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
                }
                return new SimpleString("failed");
            } else {
                if (tempAuthentication.get().isRegistered()) {
                    this.authenticationRepository.flushRepository();
                    this.professionalRepository.flushRepository();
                    System.out.println("\tEmail provided already exists: " + registerInfo.getEmail());
                    if ( photo.exists() && !photo.delete() ) {
                        System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
                    }
                    return new SimpleString("failed");
                }
            }
        }

        List<Professional> name_check = this.professionalRepository.findByFirstName(registerInfo.getFirstName());
        if ( !name_check.isEmpty() ) {
            for (Professional prof_check :
                    name_check) {
                if (prof_check.getLastName().equals(registerInfo.getLastName())) {
                    System.out.println("\tError: found professional with same First and Last name");
                    return new SimpleString("failed");
                }
                }
        }

        professional.get().setFirstName(registerInfo.getFirstName());
        professional.get().setLastName(registerInfo.getLastName());
        professional.get().setPhoto(file_path);
        professional.get().setPassword(registerInfo.getPassword());
        this.professionalRepository.save(professional.get());
        this.likesRepository.save(
                new Likes(registerInfo.getEmail(), new LinkedList<>())
        );
        System.out.println("\tRequest to add register info completed successfully");
        return new SimpleString("ok");
    }

    @PostMapping(value = "/register/add-phone", headers = "action=add-phone")
    public SimpleString addPhone(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody SimpleString simpleString
    ) {
        System.out.println("Got a request to add a phone");

        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty" );
            return new SimpleString("failed");
        }
        Authentication authentication = this.authenticationRepository.findByToken(cookie);
        if ( authentication == null ) {
            System.out.println("\tToken not in database: " + cookie);
            for ( Authentication authentication1 : authenticationRepository.findAll() ) {
                if ( !authentication1.isRegistered() ) {
                    professionalRepository.deleteAllById(authentication1.getProfid());
                }
            }
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }

        Optional<Professional> professional = this.professionalRepository.findById(authentication.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + authentication.getProfid());
            for ( Authentication authentication1 : authenticationRepository.findAll() ) {
                if ( !authentication1.isRegistered() ) {
                    professionalRepository.deleteAllById(authentication1.getProfid());
                }
            }
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }

        if ( !professional.get().getId().equals(authentication.getProfid())  ) {
            System.out.println("\tThe Id in authRep and profRep don't match: "
                    + "Auth: " + authentication.getProfid()
                    + " | Prof: " + professional.get().getId() );
            for ( Authentication authentication1 : authenticationRepository.findAll() ) {
                if ( !authentication1.isRegistered() ) {
                    professionalRepository.deleteAllById(authentication1.getProfid());
                }
            }
            this.authenticationRepository.flushRepository();
            this.professionalRepository.flushRepository();
            return new SimpleString("failed");
        }

        professional.get().setPhone(simpleString.getData());
        professionalRepository.save(professional.get());

        String newToken = authTokenMaker.makeToken(authentication.getToken());
        authentication.setToken(newToken);
        authentication.setRegistered(true);
        authenticationRepository.save(authentication);
        this.authenticationRepository.flushRepository();
        this.professionalRepository.flushRepository();
        System.out.println("\tRequest to add a phone completed successfully");
        System.out.println("Registration completed: " + professional + " | " + authentication);
        return new SimpleString(newToken);
    }

}
