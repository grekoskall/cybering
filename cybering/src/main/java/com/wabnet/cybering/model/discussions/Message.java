package com.wabnet.cybering.model.discussions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    String timeStamp;
    String sender;
    String message;

    public Message(String timeStamp, String sender, String message) {
        this.timeStamp = timeStamp;
        this.sender = sender;
        this.message = message;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id='" + id + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
