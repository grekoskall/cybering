package com.wabnet.cybering.model.signin.response;

public class SignInResponse {
    private String token;
    private String role;

    public SignInResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public SignInResponse() {
    }

    @Override
    public String toString() {
        return "SignInResponse{" +
                "token='" + token + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
