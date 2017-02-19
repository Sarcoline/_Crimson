package com.crimson.core.dao;

import com.crimson.core.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


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
        List<Rating> unsortedList = user.getUserRatings();

        (user.getUserRatings()).sort(Comparator.comparingInt(Rating::getValue));
        unsortedList.forEach(rating -> sortedList.add(rating.getTvShowRating()));

        Collections.reverse(sortedList);
        return sortedList;
    }

    @Override
    public List<Episode> getAllUnwatchedUserEpisodes(User user) {

        List<TvShow> allFollowedUserTvShows = user.getUserTvShowList();

        List<Episode> allUnwatchedUserEpisodes = new ArrayList<>();

        List<Episode> allWatchedUserEpisodes = user.getUserEpisodeList();

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
        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(days);
        Date lastDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        for (Episode episode: getAllUnwatchedUserEpisodes(user)) {
            Date episodeDate = episode.getReleaseDate();
            if(episodeDate.after(currentDate) && episodeDate.before(lastDate))allFutureUserEpisodes.add(episode);

        }

        allFutureUserEpisodes.sort(Comparator.comparing(Episode::getReleaseDate));
        return allFutureUserEpisodes;
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
        Session session = sf.getCurrentSession();
        if (!user.getUserRatings().contains(rating)) {
            user.getUserRatings().add(rating);
        }
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating) {
        if (user.getUserRatings().contains(rating)) {
            user.getUserRatings().remove(rating);
        }
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
        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        }
        session.saveOrUpdate(user);
    }


}