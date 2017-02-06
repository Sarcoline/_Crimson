package com.crimson.core.service;

import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Rating;

import java.util.List;


public interface RatingService {
    void saveRating(Rating rating);

    void deleteRating(Rating rating);

    void updateRating(Rating rating);

    Rating getRatingByIdUser(Long idUser);

    Rating getRatingByID(Long id);

    List<Rating> getRatingByIdTvShow(Long idTvShow);

    List<Rating> getAllRatings();

    Rating getRating(long idtv, long iduser);

    void saveUserRating(UserDTO userDTO, TvShowDTO tvShowDTO, int value);

    void calculateRating(long id);
}
