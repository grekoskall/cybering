package com.wabnet.cybering.repository.posts;

import com.wabnet.cybering.model.articles.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface LikesRepository extends MongoRepository<Likes, String> {

}
