package com.crimson.core.service;

import com.crimson.core.dao.RoleDAO;
import com.crimson.core.model.Role;
import com.crimson.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoleService {

    void saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void deleteRole(Role role);

    void updateRole(Role role);

    void addUser2Role(User user, Role role);

    void deleteUserFromRole(User user, Role role);
}
