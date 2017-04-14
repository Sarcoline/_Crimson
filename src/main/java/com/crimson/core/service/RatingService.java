package com.crimson.core.service;

import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;


public interface RatingService {
    void saveRating(Rating rating);

    void deleteRating(Rating rating);

    void updateRating(Rating rating);

    Rating getRatingByIdUser(Long idUser);

    Rating getRatingByID(Long id);

    List<Rating> getRatingByIdTvShow(Long idTvShow);

    List<Rating> getAllRatings();

    Rating getRating(long idtv, String username);

    void addTvShow2Rating(Rating rating, TvShow tvShow);

    void deleteTvShowFromRating(Rating rating, TvShow tvShow);

    void addUser2Rating(Rating rating, User user);

    void deleteUserFromRating(Rating rating, User user);

    void saveUserRating(String username, long id, int value);

    void calculateRating(long id);
}
