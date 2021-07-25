package com.wabnet.cybering.repository.validation;

import com.wabnet.cybering.model.signin.tokens.AuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "AuthToken")
public interface ValidationRepository extends MongoRepository<AuthToken, String> {
}
