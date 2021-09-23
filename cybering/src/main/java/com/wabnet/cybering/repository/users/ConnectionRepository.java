package com.wabnet.cybering.repository.users;


import com.wabnet.cybering.model.users.Connections;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.LinkedList;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "Connections", path="connections")
public interface ConnectionRepository extends MongoRepository<Connections, String> {

    default boolean areConnected(String profid1, String profid2) {
        Optional<Connections> connection = this.findById(profid1);
        if (connection.isEmpty())
            return false;

        LinkedList<String> connectionsList = connection.get().getList();
        return connectionsList.contains(profid2);
    }

    default boolean createUsersConnection(String profid1, String profid2) {
        Optional<Connections> connection1 = this.findById(profid1);
        Optional<Connections> connection2 = this.findById(profid2);
        if (connection1.isEmpty() || connection2.isEmpty())
            return false;

        LinkedList<String> list1 = connection1.get().getList();
        list1.add(profid2);
        connection1.get().setList(list1);
        this.save(connection1.get());

        LinkedList<String> list2 = connection2.get().getList();
        list2.add(profid1);
        connection2.get().setList(list2);
        this.save(connection2.get());

        return true;
    }
}
