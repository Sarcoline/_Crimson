package com.crimson.core.dao;


import com.crimson.core.model.Role;
import com.crimson.core.model.User;

import java.util.List;

public interface RoleDAO {

    void saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void deleteRole(Role role);

    void updateRole(Role role);

    //User2Role
    void addUser2Role(User user, Role role);

    void deleteUserFromRole(User user, Role role);
}
