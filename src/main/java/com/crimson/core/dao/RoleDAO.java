package com.crimson.core.dao;


import com.crimson.core.model.Role;
import com.crimson.core.model.User;

import java.util.List;

public interface RoleDAO {

    void save(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void delete(Role role);

    void update(Role role);

    //User2Role
    void addUser2Role(User user, Role role);

    void deleteUserFromRole(User user, Role role);
}
