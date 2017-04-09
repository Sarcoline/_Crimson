package com.crimson.core.service;

import com.crimson.core.dao.RoleDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.Role;
import com.crimson.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void saveRole(Role role) {
        roleDAO.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDAO.getById(id);
    }

    @Override
    public void deleteRole(Role role) {
        roleDAO.delete(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDAO.update(role);
    }

    @Override
    public void addUser2Role(User user, Role role) {
        if (!roleDAO.getUsers(role).contains(user)) {
            List<User> users = roleDAO.getUsers(role);
            users.add(user);
            role.setUsers(users);
            roleDAO.update(role);
        }
        if (!userDAO.getRoles(user).contains(role)) {
            List<Role> roles = userDAO.getRoles(user);
            roles.add(role);
            user.setRoles(roles);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteUserFromRole(User user, Role role) {
        if (roleDAO.getUsers(role).contains(user)) {
            List<User> users = roleDAO.getUsers(role);
            users.remove(user);
            role.setUsers(users);
            roleDAO.update(role);
        }
        if (userDAO.getRoles(user).contains(role)) {
            List<Role> roles = userDAO.getRoles(user);
            roles.remove(role);
            user.setRoles(roles);
            userDAO.update(user);
        }
    }

    @Override
    public List<User> getUsers(Role role) {
        return roleDAO.getUsers(role);
    }
}
