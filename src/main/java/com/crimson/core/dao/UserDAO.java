package com.crimson.core.dao;

import com.crimson.core.model.*;

import java.util.List;


public interface UserDAO {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(User user);

    void updateUser(User user);

    User getUserByName(String name);

    //Extra methods
    List<TvShow> getUserTvShowsSortedByMaxRating(User user);

    List<Episode> getAllUnwatchedUserEpisodes(User user);

    List<Episode> getAllUpcomingUserEpisodes(User user);

    void addTvShow2User(User user, TvShow tvShow);

    void deleteTvShowFromUser(User user, TvShow tvShow);

    //User2Episode
    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    void addRating2User(User user, Rating rating);

    void deleteRatingFromUser(User user, Rating rating);

    void addSetting2User(User user, Setting setting);

    void deleteSettingFromUser(User user, Setting setting);

    void addRole2User(User user, Role role);

    void deleteRoleFromUser(User user, Role role);
}
