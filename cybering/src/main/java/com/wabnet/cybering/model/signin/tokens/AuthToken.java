package com.wabnet.cybering.model.signin.tokens;

import org.springframework.data.annotation.Id;

import javax.annotation.processing.Generated;

public class AuthToken {
    @Id
    public String SESSION_TOKEN;

    public AuthToken(String SESSION_TOKEN) {
        this.SESSION_TOKEN = SESSION_TOKEN;
    }

    public String getSESSION_TOKEN() {
        return SESSION_TOKEN;
    }

    public void setSESSION_TOKEN(String SESSION_TOKEN) {
        this.SESSION_TOKEN = SESSION_TOKEN;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "SESSION_TOKEN='" + SESSION_TOKEN + '\'' +
                '}';
    }
}
