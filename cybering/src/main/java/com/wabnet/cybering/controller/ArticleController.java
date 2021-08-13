package com.wabnet.cybering.controller;


import com.wabnet.cybering.model.articles.Article;
import com.wabnet.cybering.model.articles.ArticleResponse;
import com.wabnet.cybering.model.articles.Likes;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Connections;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.posts.ArticlesRepository;
import com.wabnet.cybering.repository.posts.LikesRepository;
import com.wabnet.cybering.repository.users.ConnectionRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.DateComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    private final AuthenticationRepository authenticationRepository;
    private final ArticlesRepository articlesRepository;
    private final ProfessionalRepository professionalRepository;
    private final ConnectionRepository connectionRepository;
    private final LikesRepository likesRepository;

    public ArticleController(AuthenticationRepository authenticationRepository, ArticlesRepository articlesRepository, ProfessionalRepository professionalRepository, ConnectionRepository connectionRepository, LikesRepository likesRepository) {
        this.authenticationRepository = authenticationRepository;
        this.articlesRepository = articlesRepository;
        this.professionalRepository = professionalRepository;
        this.connectionRepository = connectionRepository;
        this.likesRepository = likesRepository;
    }


    @PostMapping(value="/cybering/home-page", headers = "action=article-list")
    public Object[] articleList(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to article list.");
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
        Optional<Professional> professional = this.professionalRepository.findByEmail(token.getEmail());
        if ( professional.isEmpty() ) {
            System.out.println("\tThe email in authRep doesn't belong to a professional yet: " + token.getEmail());
            return null;
        }
        Optional<Connections> connections = connectionRepository.findById(professional.get().getEmail());

        // Find articles the user posted
        List<Article> articleList =  articlesRepository.findAllByEmail(token.getEmail());
        // Find articles the user's connections posted and merge
        if ( connections.isEmpty() ) {
            System.out.println("\tThis user doesn't have any connections: " + token.getEmail());
        } else {
            for (String connection_email: connections.get().getList() ) {
                articleList.addAll(
                        articlesRepository.findAllByEmail(connection_email)
                );
            }
        }
        // articles of non-connections that connections have shown interest
        if ( connections.isEmpty() ) {
            System.out.println("\tThis user doesn't have any connections: " + token.getEmail());
        } else {
            for (String connection_email: connections.get().getList() ) {
                Optional<Likes> articles = likesRepository.findById(connection_email);
                if ( articles.isEmpty() ) {
                    continue;
                }
                LinkedList<String> art_ids = articles.get().getArticle_ids();

                for (String id: art_ids ) {
                    Optional<Article> art = articlesRepository.findById(id);
                    if ( art.isEmpty() ) {
                        continue;
                    }
                    int flag = 0;
                    for (Article aaa :
                            articleList) {
                        if (aaa.getId().equals(art.get().getId())) {
                            flag = 1;
                            break;
                        }
                    }
                    if ( articleList.contains(art.get()) || flag == 1  ) {
                        continue;
                    }
                    articleList.add(art.get());
                }
            }
        }
        articleList.sort(new DateComparator());

        List<ArticleResponse> articleResponseList = new LinkedList<>();

        for (Article asas :
                articleList) {
            Optional<Professional> prof = this.professionalRepository.findByEmail(asas.getEmail());
            LinkedList<String> articleLikes = new LinkedList<>();
            for ( String like_email : asas.getLikes() ) {
                Optional<Professional> prof2 = this.professionalRepository.findByEmail(like_email);
                prof2.ifPresent(value -> articleLikes.add(
                        value.getFirstName() + " " + value.getLastName()
                ));
            }
            LinkedList<String[]> articleComments = new LinkedList<>();
            for ( String[] comment : asas.getComments() ) {
                Optional<Professional> prof2 = this.professionalRepository.findByEmail(comment[0]);
                prof2.ifPresent(value -> articleComments.add(new String[]{value.getFirstName(), value.getLastName(), comment[1]}));
            }
            boolean likes = false;
            for ( String email: asas.getLikes() ) {
                if ( email.equals(token.getEmail()) ) {
                    likes = true;
                    break;
                }
            }
            Boolean finalLikes = likes;
            prof.ifPresent(value -> articleResponseList.add(
                    new ArticleResponse(
                            asas.getId(),
                            asas.getTitle(),
                            value.getFirstName(),
                            value.getLastName(),
                            asas.getPhoto_url(),
                            asas.getTimestamp(),
                            asas.getCategories(),
                            asas.getText(),
                            articleComments.toArray(new String[0][]),
                            articleLikes.toArray(new String[0]),
                            asas.getMedia(),
                            finalLikes
                    )
            ));
        }

        System.out.println("\tReturning a article list.");
        return articleResponseList.toArray();
    }
}
