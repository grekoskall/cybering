package com.wabnet.cybering.model.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admins {
    @Id
    private String email;
    private String password;

    public Admins(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admins() {
    }

    @Override
    public String toString() {
        return "Admins{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
