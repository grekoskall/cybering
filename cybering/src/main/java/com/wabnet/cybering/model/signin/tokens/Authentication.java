package com.wabnet.cybering.model.signin.tokens;

import org.springframework.data.annotation.Id;

public class Authentication {
    @Id
    private String token;
    private String profid;
    private boolean registered;

    public Authentication() {
    }
    public Authentication(String token, String profid, boolean registered) {
        this.token = token;
        this.profid = profid;
        this.registered = registered;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String TOKEN) {
        this.token = TOKEN;
    }

    public String getProfid() {
        return profid;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "token='" + token + '\'' +
                ", profid='" + profid + '\'' +
                ", registered=" + registered +
                '}';
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

}
