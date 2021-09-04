package com.wabnet.cybering.model.discussions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DiscussionReply {
    @Id
    String id;
    String message;

    public DiscussionReply(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public DiscussionReply() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DiscussionReply{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}


