package com.wabnet.cybering.repository.notifications;

import com.wabnet.cybering.model.notifications.ConnectionRequests;
import com.wabnet.cybering.model.notifications.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "Notifications", path="notifications")
public interface NotificationsRepository extends MongoRepository<Notifications, String> {
}
