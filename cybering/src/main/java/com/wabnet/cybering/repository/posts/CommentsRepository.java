package com.wabnet.cybering.repository.posts;

import com.wabnet.cybering.model.articles.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comments, String> {
}
