package com.wabnet.cybering;

import com.wabnet.cybering.model.articles.Article;
import com.wabnet.cybering.model.articles.Comments;
import com.wabnet.cybering.model.articles.Likes;
import com.wabnet.cybering.model.discussions.Discussion;
import com.wabnet.cybering.model.discussions.Message;
import com.wabnet.cybering.model.notifications.Notifications;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Admins;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.discussions.DiscussionsRepository;
import com.wabnet.cybering.repository.notifications.ConnectionRequestsRepository;
import com.wabnet.cybering.repository.notifications.NotificationsRepository;
import com.wabnet.cybering.repository.posts.*;
import com.wabnet.cybering.repository.users.AdminRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	@Autowired
	private final ConnectionRequestsRepository connectionRequestsRepository;
	@Autowired
	private final NotificationsRepository notificationsRepository;


	public CyberingApplication(ProfessionalRepository professionalRepository, AuthenticationRepository authenticationRepository, ConnectionRepository connectionRepository, ArticlesRepository articlesRepository, LikesRepository likesRepository, AdminRepository adminRepository, CommentsRepository commentsRepository, AdvertisementsRepository advertisementsRepository, DiscussionsRepository discussionsRepository, ConnectionRequestsRepository connectionRequestsRepository, NotificationsRepository notificationsRepository) {
		this.professionalRepository = professionalRepository;
		this.authenticationRepository = authenticationRepository;
		this.connectionRepository = connectionRepository;
		this.articlesRepository = articlesRepository;
		this.likesRepository = likesRepository;
		this.adminRepository = adminRepository;
		this.commentsRepository = commentsRepository;
		this.advertisementsRepository = advertisementsRepository;
		this.discussionsRepository = discussionsRepository;
		this.connectionRequestsRepository = connectionRequestsRepository;
		this.notificationsRepository = notificationsRepository;
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
		connectionRequestsRepository.deleteAll();
		notificationsRepository.deleteAll();
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

		String aliceId = this.professionalRepository.findByEmail("alicesm@yahop.ok").get().getId();
		String bobId = this.professionalRepository.findByEmail("mail@at.ok").get().getId();
		String jacobId = this.professionalRepository.findByEmail("jacobsm@yahop.ok").get().getId();

		notificationsRepository.save(new Notifications(aliceId));
		notificationsRepository.save(new Notifications(bobId));
		notificationsRepository.save(new Notifications(jacobId));

		likesRepository.save(
				new Likes(aliceId, new LinkedList<>() {
				})
		);

		likesRepository.save(
				new Likes(bobId, new LinkedList<>() {
				})
		);

		likesRepository.save(
				new Likes(jacobId, new LinkedList<>() {
				})
		);

		authenticationRepository.save(new Authentication("admin_token", "admin@mail.ko", true));
		authenticationRepository.save(new Authentication("alices_token", aliceId, true));
		authenticationRepository.save(new Authentication("bobs_token", bobId, true));
		authenticationRepository.save(new Authentication("jacobs_token", jacobId, true));


		LinkedList<String> connections = new LinkedList<>();
		connections.add(jacobId);
		connections.add(aliceId);
		Connections entity = new Connections(bobId, connections);
		connectionRepository.save(entity);

		LinkedList<String> connections2 = new LinkedList<>();
		connections2.add(jacobId);
		connections2.add(bobId);
		Connections entity2 = new Connections(aliceId, connections2);
		connectionRepository.save(entity2);

		LinkedList<String> connections3 = new LinkedList<>();
		connections3.add(aliceId);
		connections3.add(bobId);
		Connections entity3 = new Connections(jacobId, connections3);
		connectionRepository.save(entity3);

		articlesRepository.save(
				new Article(
						"1",
						"CEO at Intrawebel",
						aliceId,
						"dpp.jpg",
						"11/08/2021 11:13",
						new String[] {"Technology", "Environment", "Code Marathon"},
						"\"Beauty lies in the hands of the codeholder\"\n\nToday marks the start of the Intrawebel Annual Code Marathon(ACM), we will be accepting applicants until 18/8.\n\nThe theme this year will be an application that can help better the environment and urge people to become more environment friendly!\n\nWe are looking forward to the new applications this year, have fun coding!",
						new String[][] {new String[] {bobId, "This is a great opportunity and a great theme. I am up to the challenge :)"}, new String[] {jacobId, "What a perfect way to show concern about the environment and promote talent, keep being awesome!"} },
						new String[] {jacobId, bobId},
						new String[][] {new String[] {"image", "/assets/foxwhite.jpg"}}
				)
		);
		Likes like3 = likesRepository.findById(jacobId).get();
		like3.getArticle_ids().add("1");
		likesRepository.save(like3);

		Likes like4 = likesRepository.findById(bobId).get();
		like4.getArticle_ids().add("1");
		likesRepository.save(like4);
		articlesRepository.save(
				new Article(
						"2",
						"Recruiter at Loopsterdam",
						jacobId,
						"dpp.jpg",
						"11/08/2021 11:27",
						new String[] {"Recruiting"},
						"I am announcing that the recruiting process for the summer season has ended, all applicants that passed will be privately messaged at the first week of September.\n\nCongratulations to all applicants and have a great summer holiday.",
						new String[][] {new String[] {bobId, "Congratulations to all applicants! I am looking forward to the results."}},
						new String[] {aliceId, bobId},
						new String[][] {new String[] {"video", "https://www.youtube-nocookie.com/embed/1Bix44C1EzY"}}
				)
		);

		Likes like1 = likesRepository.findById(aliceId).get();
		like1.getArticle_ids().add("2");
		likesRepository.save(like1);
		likesRepository.findById(bobId).get().getArticle_ids().add("2");
		Likes like2 = likesRepository.findById(bobId).get();
		like2.getArticle_ids().add("2");
		likesRepository.save(like2);
		articlesRepository.save(
				new Article(
						"3",
						"Junior Programmer at Intrawebex",
						bobId,
						"dpp.jpg",
						"11/08/2021 11:33",
						new String[] {"Technology"},
						"I have made my very own coding language! :^)\n\nCheck it out: codewithpopstar",
						new String[][] {new String[] {aliceId, "Very creative and innovative!"}},
						new String[] {aliceId},
						new String[][] {new String[] {"", ""}}
				)
		);
		Likes like = likesRepository.findById(aliceId).get();
		like.getArticle_ids().add("3");
		likesRepository.save(like);

		commentsRepository.save(
				new Comments(
						bobId,
						new LinkedList<String>(List.of(new String[]{"1", "2"}))
				)
		);
		commentsRepository.save(
				new Comments(
						aliceId,
						new LinkedList<String>(List.of(new String[]{"3"}))
				)
		);
		commentsRepository.save(
				new Comments(
						jacobId,
						new LinkedList<String>(List.of(new String[]{"1"}))
				)
		);

		Message message1 = new Message("11/08/2021 11:33", bobId, "Hello Alice! How are you today? :)");
		Message message2 = new Message("11/08/2021 21:35", aliceId, "Aloha. I'm fine, just a bit tired. Hbu?");
		Message message3 = new Message("11/08/2021 21:36", bobId, "Fixing some API issues rn. Just go home and get some sleep then. See you tomorrow...");
		Message[] messageArray = new Message[]{message1, message2, message3};

		discussionsRepository.save(
				new Discussion(
						"1",
						bobId,
						aliceId,
						messageArray
				)
		);

		message1 = new Message("11/08/2021 11:33", bobId, "Hello Jacob! How are you today? :)");
		message2 = new Message("11/08/2021 18:35", jacobId, "Aloha Bob. I'm fine, just a bit high on coke, bitch. Hbu?");
		message3 = new Message("11/08/2021 19:42", bobId, "Fixing some API issues rn. Just go home and get some sleep then. See you tomorrow...");
		messageArray = new Message[]{message1, message2, message3};

		discussionsRepository.save(
				new Discussion(
						"2",
						jacobId,
						bobId,
						messageArray
				)
		);
	}

}
