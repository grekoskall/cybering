package com.wabnet.cybering.repository.discussions;

import com.wabnet.cybering.model.discussions.Discussion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Discussion", path = "discussion")
public interface DiscussionsRepository extends MongoRepository<Discussion, String> {

    @RestResource(path = "discussion", rel = "Discussion")
    List<Discussion> findAllByParticipant1(@Param("participant1") String participant1);

    @RestResource(path = "discussion", rel = "Discussion")
    List<Discussion> findAllByParticipant2(@Param("participant2") String participant2);
}
