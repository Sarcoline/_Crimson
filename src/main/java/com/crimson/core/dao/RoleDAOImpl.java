package com.crimson.core.dao;

import com.crimson.core.model.Role;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(role);
    }

    @Override
    @Cacheable("myCache")
    public List<Role> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Role a", Role.class).getResultList();
    }

    @Override
    @Cacheable("myCache")
    public Role getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Role.class, id);
    }

    @Override
    public void delete(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(role);
    }

    @Override
    public void update(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(role);
    }

    //RELATIONSHIPS

    //User2Role
    @Override
    public void addUser2Role(User user, Role role) {
        Session session = sessionFactory.getCurrentSession();
        role.getUsers().add(user);
        session.saveOrUpdate(role);
    }

    @Override
    public void deleteUserFromRole(User user, Role role) {
        Session session = sessionFactory.getCurrentSession();
        role.getUsers().remove(user);
        session.saveOrUpdate(role);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(Role role) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.roles r where r.idRole = ?";
        return session.createQuery(hql)
                .setParameter(0, role.getIdRole())
                .getResultList();
    }

}
