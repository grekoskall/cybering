package com.wabnet.cybering.model.discussions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Document
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    String participant1, participant2;
    Message[] messagesArray;

    public Discussion(String participant1, String participant2, Message[] messagesArray) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.messagesArray = messagesArray;
    }

    public Discussion(String id, String participant1, String participant2, Message[] messagesArray) {
        this.id = id;
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.messagesArray = messagesArray;
    }

    public Discussion(String participant1, String participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.messagesArray = new Message[0];
    }

    public Discussion() {
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "id='" + id + '\'' +
                ", participant1='" + participant1 + '\'' +
                ", participant2='" + participant2 + '\'' +
                ", messagesArray=" + Arrays.toString(messagesArray) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }

    public Message[] getMessagesArray() {
        return messagesArray;
    }

    public void setMessagesArray(Message[] messagesArray) {
        this.messagesArray = messagesArray;
    }
}
