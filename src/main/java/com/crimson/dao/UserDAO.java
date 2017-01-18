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
    public void addUser2TvShows(User user, TvShow tvShow){
        if (!user.Users2TvShow.contains(tvShow))
        {
            user.Users2TvShow.add(tvShow);
        }
        if (!tvShow.TvShows2User.contains(user)){
            tvShow.TvShows2User.add(user);
        }
    }

    public void deleteUser2TvShow(User user, TvShow tvShow){
        if(user.Users2TvShow.contains(tvShow)){
            user.Users2TvShow.remove(tvShow);
        }
        if (tvShow.TvShows2User.contains(user)){
            tvShow.TvShows2User.remove(user);
        }
    }

    //EpisodeWatched(User2Episode)
    public void addUser2Episode(User user, Episode episode){
        if (!user.Users2Episode.contains(episode)){
            user.Users2Episode.add(episode);
        }
        if (!episode.Episode2Users.contains(user)){
            episode.Episode2Users.add(user);
        }
    }

    public  void delelteUser2Episode(User user, Episode episode){
        if (user.Users2Episode.contains(episode)){
            user.Users2Episode.remove(episode);
        }
        if (episode.Episode2Users.contains(user)){
            episode.Episode2Users.remove(user);
        }
    }





}