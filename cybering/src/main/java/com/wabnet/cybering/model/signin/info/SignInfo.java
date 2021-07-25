package com.wabnet.cybering.model.signin.info;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SignInfo {
    @Id
    private String email;
    private String password;

    public SignInfo(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "SignInfo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
