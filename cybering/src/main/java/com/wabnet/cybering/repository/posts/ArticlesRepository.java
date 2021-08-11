package com.wabnet.cybering.repository.posts;

import com.wabnet.cybering.model.articles.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Articles", path="articles")
public interface ArticlesRepository  extends MongoRepository<Article, String> {

    @RestResource(path = "articles", rel="Articles")
    List<Article> findAllByEmail(@Param("email") String email);


}
