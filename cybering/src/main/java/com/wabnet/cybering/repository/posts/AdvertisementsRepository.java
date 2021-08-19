package com.wabnet.cybering.repository.posts;

import com.wabnet.cybering.model.advertisements.AdvertisementFull;
import com.wabnet.cybering.model.articles.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Advertisements", path="advertisements")
public interface AdvertisementsRepository extends MongoRepository<AdvertisementFull, String> {

    @RestResource(path = "advertisements", rel="Advertisements")
    List<AdvertisementFull> findAllByEmail(@Param("email") String email);


}
