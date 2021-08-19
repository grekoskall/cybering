package com.wabnet.cybering.repository.posts;

import com.wabnet.cybering.model.advertisements.Applications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationsRepository extends MongoRepository<Applications, String> {
}
