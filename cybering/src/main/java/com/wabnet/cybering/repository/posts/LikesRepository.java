package com.wabnet.cybering.repository.posts;

import com.wabnet.cybering.model.articles.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LikesRepository extends MongoRepository<Likes, String> {

}
