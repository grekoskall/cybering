package com.wabnet.cybering.repository.reactive.users;

import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveProfessionalRepository extends ReactiveMongoRepository<Professional, String> {

}
