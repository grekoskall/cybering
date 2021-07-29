package com.wabnet.cybering;

import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CyberingApplication implements CommandLineRunner {

	@Autowired
	private final ProfessionalRepository professionalRepository;
	@Autowired
	private final AuthenticationRepository authenticationRepository;

	public CyberingApplication(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository) {
		this.professionalRepository = professionalRepository;
		this.authenticationRepository = authenticationRepository;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CyberingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		professionalRepository.deleteAll();
		authenticationRepository.deleteAll();
		professionalRepository.save(new Professional("Alice", "Smith", 35, "alicesm@yahop.ok", "alica"));
		professionalRepository.save(new Professional("Bob", "Smith", 36,  "mail@at.ok", "bob"));
		professionalRepository.save(new Professional("Jacob", "Smith", 39, "jacobsm@yahop.ok", "jacob"));
		authenticationRepository.save(new Authentication("alices_token", "alicesm@yahop.ok", true));
		authenticationRepository.save(new Authentication("bobs_token", "mail@at.ok", true));
		authenticationRepository.save(new Authentication("jacobs_token", "jacobsm@yahop.ok", true));
	}

}
