package com.wabnet.cybering.model.signin.tokens;

import org.springframework.data.annotation.Id;

public class Authentication {
    @Id
    private String token;
    private String email;
    private boolean registered;

    public Authentication() {
    }
    public Authentication(String token, String email, boolean registered) {
        this.token = token;
        this.email = email;
        this.registered = registered;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String TOKEN) {
        this.token = TOKEN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", registered=" + registered +
                '}';
    }
}
