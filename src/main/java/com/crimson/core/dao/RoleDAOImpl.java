package com.crimson.core.dao;

import com.crimson.core.model.Role;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Role a", Role.class).getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Role.class, id);
    }

    @Override
    public void deleteRole(Role role) {
        Session session =  sessionFactory.getCurrentSession();
        session.delete(role);
    }

    @Override
    public void updateRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(role);
    }

    //RELATIONSHIPS

    //User2Role
    @Override
    public void addUser2Role(User user, Role role){
        Session session = sessionFactory.getCurrentSession();
        if (!role.getUsersFromRoles().contains(user)){
            role.getUsersFromRoles().add(user);
        }
        session.saveOrUpdate(role);
    }

    @Override
    public void deleteUserFromRole(User user, Role role){
        Session session = sessionFactory.getCurrentSession();
        if (role.getUsersFromRoles().contains(user)){
            role.getUsersFromRoles().remove(user);
        }
        session.saveOrUpdate(role);
    }

}
