package com.wabnet.cybering.repository.users;


import com.wabnet.cybering.model.users.Connections;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "Connections", path="connections")
public interface ConnectionRepository extends MongoRepository<Connections, String> {


}
