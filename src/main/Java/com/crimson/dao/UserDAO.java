package com.crimson.dao;

import com.crimson.model.TvShow;
import com.crimson.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class UserDAO {

    @Autowired
    private SessionFactory sf;
    @Autowired
    TvShowDAO tvShowDAO;

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
        for(TvShow tvshow : tvShowDAO.getAllTvShows()) {
            if (user.getTvShows().indexOf(tvshow) > -1)
                deleteUser2TvShow(user, tvshow);
        }
        session.delete(user);
    }

    public void updateUser(User user){
        Session session = sf.getCurrentSession();
        session.update(user);
    }

    public void addUser2TvShow(User user, TvShow tvshow){
        if(user.getTvShows().indexOf(tvshow) == -1)
            user.getTvShows().add(tvshow);
        if(tvshow.getUsers().indexOf(user)== -1)
            tvshow.getUsers().add(user);
    }

    public void deleteUser2TvShow(User user,TvShow tvshow){
        if(user.getTvShows().indexOf(tvshow) > -1)
            user.getTvShows().remove(tvshow);
        if(tvshow.getUsers().indexOf(user) > -1)
            tvshow.getUsers().remove(user);
    }
}