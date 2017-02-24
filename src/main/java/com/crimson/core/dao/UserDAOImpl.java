package com.crimson.core.dao;

import com.crimson.core.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sf;


    @Override
    public void save(User user) {
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
    public void delete(User user) {
        Session session = sf.getCurrentSession();
        session.delete(user);
    }

    @Override
    public void update(User user) {
        Session session = sf.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public User getUserByName(String name) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From User a where a.name like :custName", User.class).setParameter("custName", name).getSingleResult();
    }


    @Override
    public List<TvShow> getUserTvShowsSortedByMaxRating(User user) {

        List<TvShow> sortedList = new ArrayList<>();
        List<Rating> unsortedList = user.getRatings();

        (user.getRatings()).sort(Comparator.comparingInt(Rating::getValue));
        unsortedList.forEach(rating -> sortedList.add(rating.getTvShow()));

        Collections.reverse(sortedList);
        return sortedList;
    }

    @Override
    public List<Episode> getAllUnwatchedUserEpisodes(User user) {

        List<TvShow> allFollowedUserTvShows = user.getTvShows();

        List<Episode> allUnwatchedUserEpisodes = new ArrayList<>();

        List<Episode> allWatchedUserEpisodes = user.getEpisodes();

        allFollowedUserTvShows.forEach(tvShow -> {
            List<Episode> tvShowEpisodes = tvShow.getEpisodes();
            tvShowEpisodes.forEach(episode -> {
                if (!allWatchedUserEpisodes.contains(episode)) allUnwatchedUserEpisodes.add(episode);
            });
        });
        return allUnwatchedUserEpisodes;
    }

    @Override
    public List<Episode> getAllUpcomingUserEpisodes(User user) {

        List<Episode> allFutureUserEpisodes = new ArrayList<>();
        int days = user.getSetting().getDaysOfUpcomingEpisodes();

        LocalDate currentDate = LocalDate.now();
        LocalDate lastDate = LocalDate.now().plusDays(days);

        for (Episode episode: getAllUnwatchedUserEpisodes(user)) {
            LocalDate episodeDate = episode.getReleaseDate();
            if(episodeDate.isAfter(currentDate) && episodeDate.isBefore(lastDate)) allFutureUserEpisodes.add(episode);

        }

        allFutureUserEpisodes.sort(Comparator.comparing(Episode::getReleaseDate));
        return allFutureUserEpisodes;
    }


    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addTvShow2User(User user, TvShow tvShow) {
        Session session = sf.getCurrentSession();
        if (!user.getTvShows().contains(tvShow)) {
            user.getTvShows().add(tvShow);
        }
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteTvShowFromUser(User user, TvShow tvShow) {
        Session session = sf.getCurrentSession();
        user.getTvShows().remove(tvShow);
        session.saveOrUpdate(user);
    }

    //User2Episode
    @Override
    public void addEpisode2User(User user, Episode episode) {
        Session session = sf.getCurrentSession();
        if (!user.getEpisodes().contains(episode)) {
            user.getEpisodes().add(episode);
        }
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteEpisodeFromUser(User user, Episode episode) {
        Session session = sf.getCurrentSession();
        user.getEpisodes().remove(episode);
        session.saveOrUpdate(user);
    }

    //Rating

    @Override
    public void addRating2User(User user, Rating rating) {
        Session session = sf.getCurrentSession();
        if (!user.getRatings().contains(rating)) {
            user.getRatings().add(rating);
        }
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating) {
        Session session = sf.getCurrentSession();
        user.getRatings().remove(rating);
        session.saveOrUpdate(user);
    }

    //User2Setting

    @Override
    public void addSetting2User(User user, Setting setting) {
        Session session = sf.getCurrentSession();
        user.setSetting(setting);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteSettingFromUser(User user, Setting setting) {
        Session session = sf.getCurrentSession();
        if (user.getSetting() == setting) {
            user.setSetting(null);
        }
        session.saveOrUpdate(user);
    }

    //User2Role

    @Override
    public void addRole2User(User user, Role role) {
        Session session = sf.getCurrentSession();
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
        }
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteRoleFromUser(User user, Role role) {
        Session session = sf.getCurrentSession();
        user.getRoles().remove(role);
        session.saveOrUpdate(user);
    }


}