package com.wabnet.cybering;

import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.LinkedList;


@SpringBootApplication
public class CyberingApplication implements CommandLineRunner {

	@Autowired
	private final ProfessionalRepository professionalRepository;
	@Autowired
	private final AuthenticationRepository authenticationRepository;
	@Autowired
	private final ConnectionRepository connectionRepository;

	public CyberingApplication(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository, ConnectionRepository connectionRepository) {
		this.professionalRepository = professionalRepository;
		this.authenticationRepository = authenticationRepository;
		this.connectionRepository = connectionRepository;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CyberingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		professionalRepository.deleteAll();
		authenticationRepository.deleteAll();
		connectionRepository.deleteAll();
		professionalRepository.save(
				new Professional(
						"alicesm@yahop.ok",
						"Alice",
						"Smith",
						"29",
						"+3069777777",
						"default",
						"CEO",
						"Intrawebel",
						"I like my work, but even more, i love the people involved in it.",
						new String[] {"Lead Producer", "Elixir of Life", "School Teacher", "Private School of Economics"},
						new String[] {"Masters Degree", "Business Management", "Bachelors Degree", "Cambridge Economics"},
						new String[] {"Leadership", "Marketing", "Human Resources", "Business Management", "Design", "Product Development"},
						"alice")
		);
		professionalRepository.save(
				new Professional(
						"mail@at.ok",
						"Bob",
						"Smith",
						"24",
						"+3069723457",
						"default",
						"Junior Programmer",
						"Intrawebex",
						"I like to code and make computers do BEEP BOOP BEEP BOOP",
						new String[] {"IT Support", "Gougle360"},
						new String[] {"Bachelors Degree", "National and Kapodistrian University of Athens"},
						new String[] {"Unix", "C++", "Java"},
						"bob")
		);
		professionalRepository.save(
				new Professional(
						"jacobsm@yahop.ok",
						"Jacob",
						"Smith",
						"35",
						"+3069723227",
						"default",
						"Recruiter",
						"Loopsterdam",
						"Keeping an eye out for upcoming talents.",
						new String[] {""},
						new String[] {"Bachelors Degree", "Deree College"},
						new String[] {"Recruiting", "Motivating", "Inspiring"},
						"jacob")
		);

		authenticationRepository.save(new Authentication("alices_token", "alicesm@yahop.ok", true));
		authenticationRepository.save(new Authentication("bobs_token", "mail@at.ok", true));
		authenticationRepository.save(new Authentication("jacobs_token", "jacobsm@yahop.ok", true));


		LinkedList<String> connections = new LinkedList<>();
		connections.add("jacobsm@yahop.ok");
		connections.add("alicesm@yahop.ok");
		Connections entity = new Connections("mail@at.ok", connections);
		connectionRepository.save(entity);

		LinkedList<String> connections2 = new LinkedList<>();
		connections2.add("jacobsm@yahop.ok");
		connections2.add("mail@at.ok");
		Connections entity2 = new Connections("alicesm@yahop.ok", connections2);
		connectionRepository.save(entity2);

		LinkedList<String> connections3 = new LinkedList<>();
		connections3.add("alicesm@yahop.ok");
		connections3.add("mail@at.ok");
		Connections entity3 = new Connections("jacobsm@yahop.ok", connections3);
		connectionRepository.save(entity3);
	}

}
