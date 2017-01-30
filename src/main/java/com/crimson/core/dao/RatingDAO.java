package com.crimson.core.dao;

import com.crimson.core.model.Rating;

import java.util.List;

/**
 * Created by Meow on 30.01.2017.
 */
public interface RatingDAO {
    void saveRating(Rating rating);

    void deleteRating(Rating rating);

    void updateRating(Rating rating);

    Rating getRatingByIdUser(Long idUser);

    Rating getRatingByID(Long id);

    Rating getRatingByIdTvShow(Long idTvShow);

    List<Rating> getAllRatings();

    Rating getRating(long idtv, long iduser);
}
