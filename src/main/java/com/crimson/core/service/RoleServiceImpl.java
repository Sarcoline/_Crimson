package com.crimson.core.service;

import com.crimson.core.dao.RoleDAO;
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

    @Override
    public void saveRole(Role role) {
        roleDAO.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDAO.getRoleById(id);
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
        roleDAO.addUser2Role(user, role);
    }

    @Override
    public void deleteUserFromRole(User user, Role role) {
        roleDAO.deleteUserFromRole(user, role);
    }
}
