package com.wabnet.cybering.repository.validation;


import com.wabnet.cybering.model.signin.tokens.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "Authentication")
public interface AuthenticationRepository extends MongoRepository<Authentication, String> {

    Authentication findByToken(String cookie);
    Authentication findByProfid(String profid);


    default boolean flushRepository() {
        List<Authentication> authentications = this.findAll();
        for (Authentication authentication: authentications) {
            if ( !authentication.isRegistered() ) {
                this.delete(authentication);
            }
        }
        return true;
    }
}
