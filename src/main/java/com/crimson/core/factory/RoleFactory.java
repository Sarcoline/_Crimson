package com.crimson.core.factory;


import com.crimson.core.model.Role;

public class RoleFactory {

    public Role getRole(String name) {

        Role role = null;

        switch (name.toLowerCase()) {
            case "role_1": {
                role = Role.builder()
                        .roleName("ROLE_USER")
                        .build();
                break;
            }

            case "role_2": {
                role = Role.builder()
                        .roleName("ROLE_ADMIN")
                        .build();
                break;
            }

            case "role_3": {
                role = Role.builder()
                        .roleName("ROLE_MODERATOR")
                        .build();
                break;
            }
        }

        return role;
    }
}
