package com.wabnet.cybering.repository.users;

import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalRepository extends MongoRepository<Professional, String> {
    public Professional findByFirstName(String firstName);  // Follows MONGODB annotation convention to search by 'firstName' field
    public List<Professional> findByLastName(String lastName);
}
