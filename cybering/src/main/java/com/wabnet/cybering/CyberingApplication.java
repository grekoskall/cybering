package com.wabnet.cybering;

import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.repository.validation.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberingApplication implements CommandLineRunner {

	@Autowired
	private ProfessionalRepository professionalRepository;
	@Autowired
	private ValidationRepository validationRepository;
	@Autowired
	private AuthenticationRepository authenticationRepository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CyberingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		professionalRepository.deleteAll();
		validationRepository.deleteAll();
		authenticationRepository.deleteAll();
		professionalRepository.save(new Professional("Alice", "Smith", 26, "alicesm@yahop.ok"));
		professionalRepository.save(new Professional("Bob", "Smith", 25, "mail@at.ok"));
		professionalRepository.save(new Professional("Jacob", "Smith", 29, "jacobsm@yahop.ok"));

		/*System.out.println("Professionals found with findAll():");
		System.out.println("-----------------------------------");
		for (Professional professional : professionalRepository.findAll()) {
			System.out.println(professional);
		}
		System.out.println();


		System.out.println("Professionals found with findByLastName('Smith'):");
		System.out.println("-------------------------------------------------");
		for (Professional professional : professionalRepository.findByLastName("Smith")) {
			System.out.println(professional);
		}
		System.out.println();*/

	}

}
