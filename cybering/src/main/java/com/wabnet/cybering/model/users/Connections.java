package com.wabnet.cybering.model.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.LinkedList;

@Document
public class Connections {
    // email to emails
    @Id
    String email;
    LinkedList<String> list;

    public Connections() {
    }

    public Connections(String email, LinkedList<String> list) {
        this.email = email;
        this.list = list;
    }

    public Connections(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedList<String> getList() {
        return list;
    }

    public void setList(LinkedList<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Connections{" +
                "email='" + email + '\'' +
                ", list=" + list +
                '}';
    }
}
