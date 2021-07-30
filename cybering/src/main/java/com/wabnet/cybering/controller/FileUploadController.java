package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.info.RegisterInfo;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.AuthTokenMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
    @Autowired
    private final AuthTokenMaker authTokenMaker;

    public FileUploadController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, AuthTokenMaker authTokenMaker) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
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
            return new SimpleString("failed");
        }

        String file_path = "C:/tmp/" + "PROFILE_PHOTO" + "_" + cookie.substring(0,5);
        File photo = new File(file_path);
        try {
            profilePhoto.transferTo(photo);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\tError while transferring to file: " + photo.getPath());
            if ( photo.exists() && !photo.delete() ) {
                System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
            }
            this.authenticationRepository.flushRepository();
            return new SimpleString("failed");
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
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findByEmail(registerInfo.getEmail());
        if ( professional.isPresent() ) {
            System.out.println("\tEmail provided already exists: " + registerInfo.getEmail());
            if (!authentication.isRegistered()) {
                if ( photo.exists() && !photo.delete() ) {
                    System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
                }
            }
            this.authenticationRepository.flushRepository();
            return new SimpleString("failed");
        }
        if ( !authentication.getEmail().equals(registerInfo.getEmail()) ) {
            System.out.println("\tEmail provided doesn't match the cookies': "
                    + "Assigned email: " + authentication.getEmail()
                    + " | Provided email: " + registerInfo.getEmail());
            if (!authentication.isRegistered()) {
                if ( photo.exists() && !photo.delete() ) {
                    System.out.println("\tError while deleting file on path: " + photo.getAbsolutePath());
                }
            }
            this.authenticationRepository.flushRepository();
            return new SimpleString("failed");
        }

        this.professionalRepository.save(new Professional(registerInfo.getEmail(), registerInfo.getFirstName(), registerInfo.getLastName(), file_path, registerInfo.getPassword()));
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
                    professionalRepository.deleteAllByEmail(authentication1.getEmail());
                }
            }
            this.authenticationRepository.flushRepository();
            return new SimpleString("failed");
        }

        Optional<Professional> professional = this.professionalRepository.findByEmail(authentication.getEmail());
        if (professional.isEmpty()) {
            System.out.println("\tThe email in authRep doesn't belong to a professional yet: " + authentication.getEmail());
            for ( Authentication authentication1 : authenticationRepository.findAll() ) {
                if ( !authentication1.isRegistered() ) {
                    professionalRepository.deleteAllByEmail(authentication1.getEmail());
                }
            }
            this.authenticationRepository.flushRepository();
            return new SimpleString("failed");
        }

        if ( !professional.get().getEmail().equals(authentication.getEmail())  ) {
            System.out.println("\tThe email in authRep and profRep don't match: "
                    + "Auth: " + authentication.getEmail()
                    + " | Prof: " + professional.get().getEmail() );
            for ( Authentication authentication1 : authenticationRepository.findAll() ) {
                if ( !authentication1.isRegistered() ) {
                    professionalRepository.deleteAllByEmail(authentication1.getEmail());
                }
            }
            this.authenticationRepository.flushRepository();
            return new SimpleString("failed");
        }

        professional.get().setPhone(simpleString.getData());
        professionalRepository.save(professional.get());

        String newToken = authTokenMaker.makeToken(authentication.getToken());
        authentication.setToken(newToken);
        authentication.setRegistered(true);
        authenticationRepository.save(authentication);
        this.authenticationRepository.flushRepository();
        System.out.println("\tRequest to add a phone completed successfully");
        System.out.println("Registration completed: " + professional + " | " + authentication);
        return new SimpleString(newToken);
    }

}
