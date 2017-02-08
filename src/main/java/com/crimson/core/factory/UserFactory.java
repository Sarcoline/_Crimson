package com.crimson.core.factory;

import com.crimson.core.model.User;



public class UserFactory {


    public User getUser(String name) {


        User user = null;

        switch (name.toLowerCase()) {
            case "aleks": {
                user =  User.builder()
                        .name("Aleks")
                        .email("Email@wp.pl")
                        .password("123")
                        .role("ROLE_USER")
                        .build();
                break;
            }
            case "kamil": {
                user = User.builder()
                        .name("Kamil")
                        .email("kaamil.kot@gmail.com")
                        .password("123")
                        .role("ROLE_USER")
                        .build();
                break;
            }
        }
        return user;
    }
}

