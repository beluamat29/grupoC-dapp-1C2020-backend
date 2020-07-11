package com.example.demo.model.user;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacebookUserTest {

    @Test
    public void aFacebookUserHasAEmail(){
        String mail = "pepe@gmail.com";
        FacebookUser facebookUser = new FacebookUser(mail);

        assertEquals(mail, facebookUser.getMail());
    }
}
