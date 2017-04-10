package com.crimson.core.dao;

import com.crimson.core.model.Role;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //Saving Role object to database
    @Override
    public void save(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(role);
    }

    //Getting Roles objects from database and returning list
    @Override
    @Cacheable("application-cache")
    public List<Role> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Role a", Role.class).getResultList();
    }

    //Getting Role object from database by id
    @Override
    @Cacheable("application-cache")
    public Role getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Role.class, id);
    }

    //Deleting Role object from database
    @Override
    public void delete(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(role);
    }

    //Updating Role object from database
    @Override
    public void update(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(role);
    }

    //Getting relational Users objects from Role object from database
    @Override
    public List<User> getUsers(Role role) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.roles r where r.idRole = :id";
        return session.createQuery(hql, User.class)
                .setParameter("id", role.getIdRole())
                .getResultList();
    }

    @Override
    public long RoleSize() {

        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT count(x) FROM Role x");

        return (long) q.getSingleResult();
    }

}
