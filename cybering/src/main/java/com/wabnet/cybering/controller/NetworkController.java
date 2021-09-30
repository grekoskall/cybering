package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.network.Network;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class NetworkController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
    private final ConnectionRepository connectionRepository;


    public NetworkController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, ConnectionRepository connectionRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
        this.connectionRepository = connectionRepository;
    }

    @PostMapping(value="/cybering/network", headers = "action=search")
    public String[][] search(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString simpleString) {
        System.out.println("\tGot a request to search network.");
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

        LinkedList<Professional> all = new LinkedList<>(
                this.professionalRepository.findAll()
        );
        LinkedList<String[]> search = new LinkedList<>();

        for (Professional prof :
                all) {
            if ( !simpleString.getData().contains(" ") ) {
                if (prof.getFirstName().equalsIgnoreCase(simpleString.getData())) {
                    String image;
                    if ( prof.getPhoto().equals("default") ) {
                        image = "dpp.jpg";
                    } else {
                        image = prof.getPhoto();
                    }
                    search.add(new String[] {
                            prof.getFirstName(),
                            prof.getLastName(),
                            image,
                            prof.getId()
                    });
                    continue;
                }
                if (prof.getLastName().equalsIgnoreCase(simpleString.getData())) {
                    String image;
                    if ( prof.getPhoto().equals("default") ) {
                        image = "dpp.jpg";
                    } else {
                        image = prof.getPhoto();
                    }
                    search.add(new String[] {
                            prof.getFirstName(),
                            prof.getLastName(),
                            image,
                            prof.getId()
                    });
                }
            } else {
                String[] fullname = simpleString.getData().split(" ");
                if (prof.getFirstName().equalsIgnoreCase(fullname[0])
                && prof.getLastName().equalsIgnoreCase(fullname[1])) {
                    String image;
                    if ( prof.getPhoto().equals("default") ) {
                        image = "dpp.jpg";
                    } else {
                        image = prof.getPhoto();
                    }
                    search.add(new String[] {
                            prof.getFirstName(),
                            prof.getLastName(),
                            image,
                            prof.getId()
                    });
                    continue;
                }
                if (prof.getLastName().equalsIgnoreCase(fullname[0])
                        && prof.getFirstName().equalsIgnoreCase(fullname[1])) {
                    String image;
                    if ( prof.getPhoto().equals("default") ) {
                        image = "dpp.jpg";
                    } else {
                        image = prof.getPhoto();
                    }
                    search.add(new String[] {
                            prof.getFirstName(),
                            prof.getLastName(),
                            image,
                            prof.getId()
                    });
                }
            }
        }

        if (search.isEmpty()) {
            System.out.println("\tSearch failed for: " + simpleString.toString());
            return null;
        }

        System.out.println("\tReturning search results");
        return search.toArray(new String[0][]);
    }

    @PostMapping(value="/cybering/network", headers = "action=network")
    public Network[] getNetwork(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to get network.");
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
        Optional<Connections> connections = this.connectionRepository.findById(professional.get().getId());
        if (connections.isEmpty()) {
            System.out.println("\tThis user has no connections");
            return null;
        }

        LinkedList<Network> networkLinkedList = new LinkedList<>();
        LinkedList<String> conList = connections.get().getList();
        for (String profid :
                conList) {
            Optional<Professional> professionalConnection = this.professionalRepository.findById(profid);
            if (professionalConnection.isEmpty()) {
                System.out.println("\tThis user has an unknown connection: " + professional.get());
                return null;
            }
            String image;
            if ( professional.get().getPhoto().equals("default") ) {
                image="dpp.jpg";
            } else {
                image = professional.get().getPhoto();
            }
            networkLinkedList.add(
                    new Network(
                            professionalConnection.get().getFirstName(),
                            professionalConnection.get().getLastName(),
                            professionalConnection.get().getId(),
                            image,
                            professionalConnection.get().getWorkPlace(),
                            professionalConnection.get().getWorkPosition()
                    )
            );
        }

        System.out.println("\tReturning network");
        return networkLinkedList.toArray(new Network[0]);
    }
}
