package com.wabnet.cybering.repository.notifications;

import com.wabnet.cybering.model.notifications.ConnectionRequests;
import com.wabnet.cybering.model.users.Professional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.LinkedList;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "ConnectionRequests", path="connectionrequests")
public interface ConnectionRequestsRepository extends MongoRepository<ConnectionRequests, String> {

    default boolean hasSentRequestToProfile (String currentUserProfid, String profileProfid) {
        Optional<ConnectionRequests> connectionRequests = this.findById(profileProfid);
        if (connectionRequests.isEmpty())
            return false;

        LinkedList<String> requestsList = connectionRequests.get().getRequestsList();
        return requestsList.contains(currentUserProfid);
    }

    default boolean hasReceivedRequestFromProfile (String currentUserProfid, String profileProfid) {
        Optional<ConnectionRequests> connectionRequests = this.findById(currentUserProfid);
        if (connectionRequests.isEmpty())
            return false;

        LinkedList<String> requestsList = connectionRequests.get().getRequestsList();
        return requestsList.contains(profileProfid);
    }

    default void deleteConnectionRequest (String userSentProfid, String userReceivedProfid) {
        Optional<ConnectionRequests> connectionRequest = this.findById(userReceivedProfid);
        if (connectionRequest.isEmpty())
            return;

        LinkedList<String> requestsList = connectionRequest.get().getRequestsList();
        requestsList.remove(userSentProfid);
        connectionRequest.get().setRequestsList(requestsList);
        this.save(connectionRequest.get());
    }

    default void createConnectionRequest (String userSentProfid, String userReceivedProfid) {
        Optional<ConnectionRequests> connectionRequest = this.findById(userReceivedProfid);
        if (connectionRequest.isEmpty())
            return;

        LinkedList<String> requestsList = connectionRequest.get().getRequestsList();
        requestsList.add(userSentProfid);
        connectionRequest.get().setRequestsList(requestsList);
        this.save(connectionRequest.get());
    }

}
