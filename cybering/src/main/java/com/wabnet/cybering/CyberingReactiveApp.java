package com.wabnet.cybering;

import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.reactive.users.ReactiveProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CyberingReactiveApp implements CommandLineRunner {

    @Autowired
    private ReactiveProfessionalRepository reactiveProfessionalRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CyberingReactiveApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<Professional> professionalFlux = reactiveProfessionalRepository.findAll();

        
    }
}
