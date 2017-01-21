package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
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


    public void saveUser(User user) {
        Session session = sf.getCurrentSession();
        session.persist(user);
    }

    public List<User> getAllUsers() {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From User a", User.class).getResultList();
    }

    public User getUserById(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(User.class, id);
    }

    public void deleteUser(User user) {
        Session session = sf.getCurrentSession();
        session.delete(user);
    }

    public void updateUser(User user) {
        Session session = sf.getCurrentSession();
        session.update(user);
    }

    public User getUserByName(String name) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From User a where a.name like :custName", User.class).setParameter("custName", name).getSingleResult();
    }

    //RELATIONSHIPS

    //User2TvShow

    public void addTvShow2User(User user, TvShow tvShow) {
        if (!user.getUserTvShowList().contains(tvShow)) {
            user.getUserTvShowList().add(tvShow);
        }
    }

    public void deleteTvShowFromUser(User user, TvShow tvShow) {
        if (user.getUserTvShowList().contains(tvShow)) {
            user.getUserTvShowList().remove(tvShow);
        }
    }

    //User2Episode
    public void addEpisode2User(User user, Episode episode) {
        if (!user.getUserEpisodeList().contains(episode)) {
            user.getUserEpisodeList().add(episode);
        }
    }

    public void deleteEpisodeFromUser(User user, Episode episode) {
        if (user.getUserEpisodeList().contains(episode)) {
            user.getUserEpisodeList().remove(episode);
        }
    }

    //Rating

    public void addRating2User(User user, Rating rating) {
        if (!user.getUserRatings().contains(rating)) {
            user.getUserRatings().add(rating);
        }
    }

    public void deleteRatingFromUser(User user, Rating rating) {
        if (user.getUserRatings().contains(rating)) {
            user.getUserRatings().remove(rating);
        }
    }


}