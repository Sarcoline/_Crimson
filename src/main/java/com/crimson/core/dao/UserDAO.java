package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;

/**
 * Created by Meow on 30.01.2017.
 */
public interface UserDAO {
    void saveUser(User user);

    List<?> getAllUsers();

    User getUserById(Long id);

    void deleteUser(User user);

    void updateUser(User user);

    User getUserByName(String name);

    void addTvShow2User(User user, TvShow tvShow);

    void deleteTvShowFromUser(User user, TvShow tvShow);

    //User2Episode
    void addEpisode2User(User user, Episode episode);

    void deleteEpisodeFromUser(User user, Episode episode);

    void addRating2User(User user, Rating rating);

    void deleteRatingFromUser(User user, Rating rating);
}
