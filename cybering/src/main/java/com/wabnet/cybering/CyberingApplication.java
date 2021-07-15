package com.wabnet.cybering;

import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberingApplication implements CommandLineRunner {

	@Autowired
	private ProfessionalRepository professionalRepository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CyberingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		professionalRepository.deleteAll();
		professionalRepository.save(new Professional("Alice", "Smith", 26));
		professionalRepository.save(new Professional("Bob", "Smith", 25));

		System.out.println("Professionals found with findAll():");
		System.out.println("-----------------------------------");
		for (Professional professional : professionalRepository.findAll()) {
			System.out.println(professional);
		}
		System.out.println();

		System.out.println("Professional found with findByFistName('Alice'):");
		System.out.println("-------------------------------------------------");
		System.out.println(professionalRepository.findByFirstName("Alice"));
		System.out.println();

		System.out.println("Professionals found with findByLastName('Smith'):");
		System.out.println("-------------------------------------------------");
		for (Professional professional : professionalRepository.findByLastName("Smith")) {
			System.out.println(professional);
		}
		System.out.println();
	}

}
