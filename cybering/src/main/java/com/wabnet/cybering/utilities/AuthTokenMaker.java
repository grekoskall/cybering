package com.wabnet.cybering.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenMaker {

    public String makeToken(String email, String firstName, String lastName, String password) {
        String token1 = email.length() + firstName.toLowerCase().charAt(0) + lastName.toLowerCase().charAt(0) + password.toUpperCase().substring(2, 4);
        String token2 = token1.repeat(3) + "hashed" + token1.repeat(3);
        return token2;
    }

}
