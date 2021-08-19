package com.wabnet.cybering.model.advertisements;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document
public class Applications {
    @Id
    String email;
    LinkedList<String> adIds;

    public Applications(String email, LinkedList<String> adIds) {
        this.email = email;
        this.adIds = adIds;
    }

    public LinkedList<String> getAdIds() {
        return adIds;
    }

    public void setAdIds(LinkedList<String> adIds) {
        this.adIds = adIds;
    }

    public Applications() {
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

}
