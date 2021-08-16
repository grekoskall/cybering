package com.wabnet.cybering.repository.users;

import com.wabnet.cybering.model.users.Admins;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "Admins")
public interface AdminRepository extends MongoRepository<Admins, String> {

}
