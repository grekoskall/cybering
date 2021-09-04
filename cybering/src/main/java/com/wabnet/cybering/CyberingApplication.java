package com.wabnet.cybering;

import com.wabnet.cybering.model.articles.Article;
import com.wabnet.cybering.model.articles.Comments;
import com.wabnet.cybering.model.articles.Likes;
import com.wabnet.cybering.model.discussions.Discussion;
import com.wabnet.cybering.model.discussions.Message;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.discussions.DiscussionsRepository;
import com.wabnet.cybering.repository.posts.*;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@SpringBootApplication
public class CyberingApplication implements CommandLineRunner {

	@Autowired
	private final ProfessionalRepository professionalRepository;
	@Autowired
	private final AuthenticationRepository authenticationRepository;
	@Autowired
	private final ConnectionRepository connectionRepository;
	@Autowired
	private final ArticlesRepository articlesRepository;
	@Autowired
	private final LikesRepository likesRepository;
	@Autowired
	private final AdminRepository adminRepository;
	@Autowired
	private final CommentsRepository commentsRepository;
	@Autowired
	private final AdvertisementsRepository advertisementsRepository;
	@Autowired
	private final DiscussionsRepository discussionsRepository;


	public CyberingApplication(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository, ConnectionRepository connectionRepository, ArticlesRepository articlesRepository, LikesRepository likesRepository, AdminRepository adminRepository, CommentsRepository commentsRepository, AdvertisementsRepository advertisementsRepository, DiscussionsRepository discussionsRepository) {
		this.professionalRepository = professionalRepository;
		this.authenticationRepository = authenticationRepository;
		this.connectionRepository = connectionRepository;
		this.articlesRepository = articlesRepository;
		this.likesRepository = likesRepository;
		this.adminRepository = adminRepository;
		this.commentsRepository = commentsRepository;
		this.advertisementsRepository = advertisementsRepository;
		this.discussionsRepository = discussionsRepository;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CyberingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		professionalRepository.deleteAll();
		authenticationRepository.deleteAll();
		connectionRepository.deleteAll();
		articlesRepository.deleteAll();
		likesRepository.deleteAll();
		adminRepository.deleteAll();
		commentsRepository.deleteAll();
		advertisementsRepository.deleteAll();
		discussionsRepository.deleteAll();
		adminRepository.save(
				new Admins("admin@mail.ko", "admin")
		);
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
						"alice",
						"1")
		);
		likesRepository.save(
				new Likes("alicesm@yahop.ok", new LinkedList<>() {
				})
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
						"bob",
						"1")
		);
		likesRepository.save(
				new Likes("mail@at.ok", new LinkedList<>() {
				})
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
		likesRepository.save(
				new Likes("jacobsm@yahop.ok", new LinkedList<>() {
				})
		);

		authenticationRepository.save(new Authentication("admin_token", "admin@mail.ko", true));
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

		articlesRepository.save(
				new Article(
						"1",
						"CEO at Intrawebel",
						"alicesm@yahop.ok",
						"dpp.jpg",
						"11/08/2021 11:13",
						new String[] {"Technology", "Environment", "Code Marathon"},
						"\"Beauty lies in the hands of the codeholder\"\n\nToday marks the start of the Intrawebel Annual Code Marathon(ACM), we will be accepting applicants until 18/8.\n\nThe theme this year will be an application that can help better the environment and urge people to become more environment friendly!\n\nWe are looking forward to the new applications this year, have fun coding!",
						new String[][] {new String[] {"mail@at.ok", "This is a great opportunity and a great theme. I am up to the challenge :)"}, new String[] {"jacobsm@yahop.ok", "What a perfect way to show concern about the environment and promote talent, keep being awesome!"} },
						new String[] {"jacobsm@yahop.ok", "mail@at.ok"},
						new String[][] {new String[] {"image", "/assets/foxwhite.jpg"}}
				)
		);
		Likes like3 = likesRepository.findById("jacobsm@yahop.ok").get();
		like3.getArticle_ids().add("1");
		likesRepository.save(like3);

		Likes like4 = likesRepository.findById("mail@at.ok").get();
		like4.getArticle_ids().add("1");
		likesRepository.save(like4);
		articlesRepository.save(
				new Article(
						"2",
						"Recruiter at Loopsterdam",
						"jacobsm@yahop.ok",
						"dpp.jpg",
						"11/08/2021 11:27",
						new String[] {"Recruiting"},
						"I am announcing that the recruiting process for the summer season has ended, all applicants that passed will be privately messaged at the first week of September.\n\nCongratulations to all applicants and have a great summer holiday.",
						new String[][] {new String[] {"mail@at.ok", "Congratulations to all applicants! I am looking forward to the results."}},
						new String[] {"alicesm@yahop.ok", "mail@at.ok"},
						new String[][] {new String[] {"video", "https://www.youtube-nocookie.com/embed/1Bix44C1EzY"}}
				)
		);

		Likes like1 = likesRepository.findById("alicesm@yahop.ok").get();
		like1.getArticle_ids().add("2");
		likesRepository.save(like1);
		likesRepository.findById("mail@at.ok").get().getArticle_ids().add("2");
		Likes like2 = likesRepository.findById("mail@at.ok").get();
		like2.getArticle_ids().add("2");
		likesRepository.save(like2);
		articlesRepository.save(
				new Article(
						"3",
						"Junior Programmer at Intrawebex",
						"mail@at.ok",
						"dpp.jpg",
						"11/08/2021 11:33",
						new String[] {"Technology"},
						"I have made my very own coding language! :^)\n\nCheck it out: codewithpopstar",
						new String[][] {new String[] {"alicesm@yahop.ok", "Very creative and innovative!"}},
						new String[] {"alicesm@yahop.ok"},
						new String[][] {new String[] {"", ""}}
				)
		);
		Likes like = likesRepository.findById("alicesm@yahop.ok").get();
		like.getArticle_ids().add("3");
		likesRepository.save(like);

		commentsRepository.save(
				new Comments(
						"mail@at.ok",
						new LinkedList<String>(List.of(new String[]{"1", "2"}))
				)
		);
		commentsRepository.save(
				new Comments(
						"alicesm@yahop.ok",
						new LinkedList<String>(List.of(new String[]{"3"}))
				)
		);
		commentsRepository.save(
				new Comments(
						"jacobsm@yahop.ok",
						new LinkedList<String>(List.of(new String[]{"1"}))
				)
		);

		Message message1 = new Message("11/08/2021 11:33", "mail@at.ok", "Hello Alice! How are you today? :)");
		Message message2 = new Message("11/08/2021 11:35", "alicesm@yahop.ok", "Aloha. I'm fine, just a bit tired. Hbu?");
		Message message3 = new Message("11/08/2021 11:36", "mail@at.ok", "Fixing some API issues rn. Just go home and get some sleep then. See you tomorrow...");
		Message[] messageArray = new Message[]{message1, message2, message3};

		discussionsRepository.save(
				new Discussion(
						"1",
						"mail@at.ok",
						"alicesm@yahop.ok",
						messageArray
				)
		);

		message1 = new Message("11/08/2021 11:33", "mail@at.ok", "Hello Jacob! How are you today? :)");
		message2 = new Message("11/08/2021 11:35", "jacobsm@yahop.ok", "Aloha Bob. I'm fine, just a bit high on coke, bitch. Hbu?");
		message3 = new Message("11/08/2021 11:42", "mail@at.ok", "Fixing some API issues rn. Just go home and get some sleep then. See you tomorrow...");
		messageArray = new Message[]{message1, message2, message3};

		discussionsRepository.save(
				new Discussion(
						"2",
						"jacobsm@yahop.ok",
						"mail@at.ok",
						messageArray
				)
		);
	}

}
