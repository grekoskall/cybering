package com.wabnet.cybering.repository.users;

import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "professionals", path = "professionals")
public interface ProfessionalRepository extends MongoRepository<Professional, String> {
    //public Professional findByFirstName(String firstName);  // Follows MONGODB annotation convention to search by 'firstName' field
    public List<Professional> findByLastName(String lastName);

    List<Professional> findByFirstName(@Param("name") String name);
}
