package com.crimson.core.dao;

import com.crimson.core.model.Rating;

import java.util.List;


public interface RatingDAO extends BaseDAO<Rating, Long> {

    Rating getRatingByIdUser(Long idUser);

    List<Rating> getRatingByIdTvShow(Long idTvShow);

    Rating getRating(long idtv, long iduser);
}
