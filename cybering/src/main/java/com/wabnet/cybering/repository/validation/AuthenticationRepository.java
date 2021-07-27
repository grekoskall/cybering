package com.wabnet.cybering.repository.validation;


import com.wabnet.cybering.model.signin.tokens.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends MongoRepository<Authentication, String> {


}
