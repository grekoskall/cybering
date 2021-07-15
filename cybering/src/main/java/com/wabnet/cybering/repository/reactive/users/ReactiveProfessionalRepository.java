package com.wabnet.cybering.repository.reactive.users;

import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveProfessionalRepository extends ReactiveMongoRepository<Professional, String> {

    Flux<Professional> findAllByFirstName(String firstName);
    Mono<Professional> findFirstByAge(Integer age);

}
