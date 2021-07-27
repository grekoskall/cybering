package com.wabnet.cybering.model.signin.tokens;

import org.springframework.data.annotation.Id;

public class Authentication {
    @Id
    private String TOKEN;
    private String email;

    public Authentication() {
    }

    public Authentication(String TOKEN, String email) {
        this.TOKEN = TOKEN;
        this.email = email;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
