package com.wabnet.cybering.repository.users;

import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "Professionals", path = "professionals")
public interface ProfessionalRepository extends MongoRepository<Professional, String> {
    // public Professional findByFirstName(String firstName);  // Follows MONGODB annotation convention to search by 'firstName' field
    // public List<Professional> findByLastName(String lastName);

    @RestResource(path = "firstnames", rel = "First Names")
    List<Professional> findByFirstName(@Param("firstName") String firstName);

    @RestResource(path = "emails", rel="Emails")
    Optional<Professional> findByEmail(@Param("email") String email);

    void deleteAllByEmail(String email);
}
