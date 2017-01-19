package com.crimson.dao;

import com.crimson.model.Episode;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import com.sun.istack.internal.Nullable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Repository
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

    public User getUserByName(String name){
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From User a where a.name like :custName", User.class).setParameter("custName", name).getSingleResult();
    }

    //RELATIONSHIPS

    //User2TvShow

    public void addTvShow2User(User user, TvShow tvShow){
        if (!user.getUserTvShowList().contains(tvShow)){
            user.getUserTvShowList().add(tvShow);
        }
    }

    public void deleteTvShowFromUser(User user, TvShow tvShow){
        if (user.getUserTvShowList().contains(tvShow)){
            user.getUserTvShowList().remove(tvShow);
        }
    }
}