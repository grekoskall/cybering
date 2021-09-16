package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.advertisements.*;
import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.posts.AdvertisementsRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.AdComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdvertisementsController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
    private final AdvertisementsRepository advertisementsRepository;
    private final ConnectionRepository connectionRepository;


    public AdvertisementsController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, AdvertisementsRepository advertisementsRepository, ConnectionRepository connectionRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
        this.advertisementsRepository = advertisementsRepository;
        this.connectionRepository = connectionRepository;
    }

    @PostMapping(value="/cybering/advertisements", headers = "action=get-apps")
    public AdvertisementApplication[] getApplications(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to advertisement applications list.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        LinkedList<AdvertisementFull> advertisementFulls = new LinkedList<>(
                this.advertisementsRepository.findAllByProfid(professional.get().getId())
        );

        if ( advertisementFulls.isEmpty()) {
            System.out.println("\tThis user has no advertisements");
            return null;
        }

        LinkedList<AdvertisementApplication> advertisementApplications = new LinkedList<>();
        for (AdvertisementFull ad :
                advertisementFulls) {
            advertisementApplications.add(
                    new AdvertisementApplication(
                            ad.getId(),
                            ad.getTitle(),
                            ad.getDescription(),
                            ad.getSkills().toArray(new String[0]),
                            ad.getStartDate(),
                            ad.getEndDate(),
                            ad.getApplicants().toArray(new String[0][])
                    )
            );
        }

        System.out.println("\tReturning users advertisements");
        return advertisementApplications.toArray(new AdvertisementApplication[0]);
    }



    @PostMapping(value="/cybering/advertisements", headers = "action=get-ads")
    public Advertisement[] getAdvertisements(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to get advertisements.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        LinkedList<AdvertisementFull> advertisementFulls = new LinkedList<>();

        Optional<Connections> connections = this.connectionRepository.findById(professional.get().getId());
        if ( connections.isPresent() ) {
            for (String consProfid :
                    connections.get().getList()) {
                advertisementFulls.addAll(
                        this.advertisementsRepository.findAllByProfid(
                                consProfid
                        )
                );
            }
        }

        // Collaborative Filtering
        Hashtable<String, String[]> users = new Hashtable<>();
        Hashtable<String, String[]> ads = new Hashtable<>();
        for (Professional user :
                this.professionalRepository.findAll() ) {
            if ( !user.getId().equals(professional.get().getId())) {
                users.put(
                        user.getId(),
                        user.getSkills()
                );

            }

        }
        for (AdvertisementFull advertisementFull:
             this.advertisementsRepository.findAll()) {
            if ( !advertisementFull.getProfid().equals(professional.get().getId()) ) {
                ads.put(
                        advertisementFull.getId(),
                        advertisementFull.getSkills().toArray(new String[0])
                );
            }
        }

        Set<String> userKeys = users.keySet();
        Set<String> adsKeys = ads.keySet();
        Hashtable<String, Hashtable<String, Integer>>
                ratingsTable = new Hashtable<>();

        for(String ukey: userKeys) {
            String[] userSkills = users.get(ukey);
            for(String akey: adsKeys) {
                String[] adSkills = ads.get(akey);
                int value = 0;
                for (String uSkill :
                        userSkills) {
                    for (String aSkill:
                         adSkills) {
                        if (uSkill.equals(aSkill)) {
                            value++;
                        }
                    }
                }
                Hashtable<String, Integer> valueTable = new Hashtable<>();
                valueTable.put(akey, value);
                ratingsTable.put(
                       ukey,
                       valueTable
               );
            }
        }

        int value = 0;
        Hashtable<String, Integer> valueTable = new Hashtable<>();
        String[] profSkills = professional.get().getSkills();
        for(String akey: adsKeys) {
            String[] adSkills = ads.get(akey);
            for (String pSkill :
                    profSkills) {
                for (String aSkill:
                     adSkills) {
                    if ( pSkill.equals(aSkill)) {
                        value++;
                    }
                }
            }
            valueTable.put(
                    akey,
                    value);
        }

        for (String ukey :
                ratingsTable.keySet()) {
            for (String akey:
                 ratingsTable.get(ukey).keySet()) {
                if (Objects.equals(ratingsTable.get(ukey).get(akey), valueTable.get(akey))) {
                    if ( advertisementsRepository.findById(akey).isPresent() ) {
                        boolean flag = false;
                        for (AdvertisementFull adF :
                                advertisementFulls) {
                            if (adF.getId().equals(advertisementsRepository.findById(akey).get().getId())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            advertisementFulls.add(
                                    advertisementsRepository.findById(akey).get()
                            );
                        }
                    }
                }
            }
        }

        LinkedList<Advertisement> returnAdvertisements = new LinkedList<>();
        for (AdvertisementFull advertisementFull: advertisementFulls) {
            Optional<Professional> poster = professionalRepository.findById(advertisementFull.getProfid());
            poster.ifPresent(professional1 -> returnAdvertisements.add(
                    new Advertisement(
                            advertisementFull.getId(),
                            professional1.getFirstName(),
                            professional1.getLastName(),
                            advertisementFull.getStartDate(),
                            advertisementFull.getEndDate(),
                            professional1.getWorkPlace(),
                            professional1.getWorkPosition(),
                            advertisementFull.getTitle(),
                            advertisementFull.getDescription(),
                            advertisementFull.getSkills().toArray(new String[0])
                    )
            ));
        }

        returnAdvertisements.sort(new AdComparator());
        System.out.println("\tReturning advertisements, size: " + returnAdvertisements.size());
        return returnAdvertisements.toArray(new Advertisement[0]);
    }




    @PostMapping(value="/cybering/advertisements", headers = "action=apply")
    public SimpleString apply(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString simpleString) {
        System.out.println("\tGot a request to apply to an article.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }
        Optional<AdvertisementFull> advertisementFull = this.advertisementsRepository.findById(simpleString.getData());
        if ( advertisementFull.isEmpty() ) {
            System.out.println("\tThe advertisement id given doesn't exist");
            return null;
        }

        LinkedList<String[]> applList = advertisementFull.get().getApplicants();
        applList.add( new String[] {
                professional.get().getFirstName(),
                professional.get().getLastName()
        });
        advertisementFull.get().setApplicants(applList);
        this.advertisementsRepository.save(advertisementFull.get());

        System.out.println("\tUser: " + professional.get() + " applied successfully to: " +  advertisementFull.get());
        return new SimpleString("success");
    }

    @PostMapping(value="/cybering/advertisements", headers = "action=post")
    public SimpleString post(@RequestHeader HttpHeaders httpHeaders, @RequestBody AdvertisementPost advertisementPost) {
        System.out.println("\tGot a request to post an article.");
        String cookie = httpHeaders.getFirst("Cookies");
        if ( cookie == null ) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if(token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        if ( advertisementPost.getTitle().isEmpty() || advertisementPost.getTitle().replaceAll("[ \n\t]", "").equals("")) {
            System.out.println("\tNo title given");
            return null;
        }
        if ( advertisementPost.getDescription().isEmpty() || advertisementPost.getDescription().replaceAll("[ \n\t]", "").equals("")) {
            System.out.println("\tNo desc given");
            return null;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String[] skills = advertisementPost.getSkills().split(" ");
        LinkedList<String> skillsList = new LinkedList<>(Arrays.asList(skills));
        this.advertisementsRepository.save(
                new AdvertisementFull(
                        professional.get().getId(),
                        dateTimeFormatter.format(localDateTime),
                        advertisementPost.getEndDate(),
                        advertisementPost.getTitle(),
                        advertisementPost.getDescription(),
                        skillsList,
                        new LinkedList<>()
                )
        );
        System.out.println("\tAdvertisement posted successfully: " + advertisementPost);
        return new SimpleString("success");
    }
}

