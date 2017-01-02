package com.crimson.dao;


import com.crimson.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserDAO {

    @Autowired
    private SessionFactory sf;


    public void saveUser(User user) {
        Session session = sf.getCurrentSession();
        session.persist(user);
    }

    public List<User> getAllUsers() {
        Session session = sf.getCurrentSession();
        List<User> users = session.createQuery("Select a From User a", User.class).getResultList();
        return users;
    }

    public User getUserById(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(User.class, id);
    }

    public void deleteUser(User user){
        Session session = sf.getCurrentSession();
        session.delete(user);
    }

    public void updateUser(User user){
        Session session = sf.getCurrentSession();
        session.update(user);
    }
}