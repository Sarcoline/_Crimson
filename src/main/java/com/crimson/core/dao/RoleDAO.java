package com.crimson.core.dao;


import com.crimson.core.model.Role;
import com.crimson.core.model.User;

public interface RoleDAO extends BaseDAO<Role, Long> {

    void addUser2Role(User user, Role role);

    void deleteUserFromRole(User user, Role role);
}
