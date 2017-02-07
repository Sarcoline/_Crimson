package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sf;


    @Override
    public void saveUser(User user) {
        Session session = sf.getCurrentSession();
        session.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From User a", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(User.class, id);
    }

    @Override
    public void deleteUser(User user) {
        Session session = sf.getCurrentSession();
        session.delete(user);
    }

    @Override
    public void updateUser(User user) {
        Session session = sf.getCurrentSession();
        session.update(user);
    }

    @Override
    public User getUserByName(String name) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From User a where a.name like :custName", User.class).setParameter("custName", name).getSingleResult();
    }

    //Extra methods
    @Override
    public List<TvShow> getUserTvShowsSortedByMaxRating(User user){
        List<TvShow> unsortedList = user.getUserTvShowList();

        Collections.sort(unsortedList, new Comparator<TvShow>() {
            @Override
            public int compare(TvShow o1, TvShow o2) {
                return o1.getOverallRating().compareTo(o2.getOverallRating());
            }
        });

        Collections.reverse(unsortedList);
        return unsortedList;
    }

    @Override
    public List<Episode> getAllUnwatchedUserEpisodes(User user){

        List<TvShow> allFollowedUserTvShows = user.getUserTvShowList();

        List<Episode> allUnwatchedUserEpisodes = new ArrayList<>();

        List<Episode> allWatchedUserEpisodes = user.getUserEpisodeList();

        for (TvShow tvShow : allFollowedUserTvShows){
            List<Episode> tvShowEpisodes = tvShow.getEpisodes();
            for (Episode episode : tvShowEpisodes){
                if (!allWatchedUserEpisodes.contains(episode)){
                    allUnwatchedUserEpisodes.add(episode);
                }
            }
        }
        return allUnwatchedUserEpisodes;
    }


    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addTvShow2User(User user, TvShow tvShow) {
        if (!user.getUserTvShowList().contains(tvShow)) {
            user.getUserTvShowList().add(tvShow);
        }
    }

    @Override
    public void deleteTvShowFromUser(User user, TvShow tvShow) {
        if (user.getUserTvShowList().contains(tvShow)) {
            user.getUserTvShowList().remove(tvShow);
        }
    }

    //User2Episode
    @Override
    public void addEpisode2User(User user, Episode episode) {
        if (!user.getUserEpisodeList().contains(episode)) {
            user.getUserEpisodeList().add(episode);
        }
    }

    @Override
    public void deleteEpisodeFromUser(User user, Episode episode) {
        if (user.getUserEpisodeList().contains(episode)) {
            user.getUserEpisodeList().remove(episode);
        }
    }

    //Rating

    @Override
    public void addRating2User(User user, Rating rating) {
        if (!user.getUserRatings().contains(rating)) {
            user.getUserRatings().add(rating);
        }
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating) {
        if (user.getUserRatings().contains(rating)) {
            user.getUserRatings().remove(rating);
        }
    }


}