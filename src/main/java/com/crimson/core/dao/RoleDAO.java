package com.crimson.core.dao;


import com.crimson.core.model.Role;
import com.crimson.core.model.User;

import java.util.List;

public interface RoleDAO extends BaseDAO<Role, Long> {
    List<User> getUsers(Role role);

    long RoleSize();
}
