package com.crimson.core.dao;

import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;


public interface RatingDAO extends BaseDAO<Rating, Long> {

    Rating getRatingByIdUser(Long idUser);

    List<Rating> getRatingByIdTvShow(Long idTvShow);

    Rating getRating(long idtv, long iduser);

    void addTvShow2Rating(Rating rating, TvShow tvShow);

    void deleteTvShowFromRating(Rating rating);

    void addUser2Rating(Rating rating, User user);

    void deleteUserFromRating(Rating rating);
}
