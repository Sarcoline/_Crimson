package com.crimson.core.dao;

import com.crimson.core.model.*;


public interface UserDAO extends BaseDAO<User, Long> {

    User getUserByName(String name);

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
