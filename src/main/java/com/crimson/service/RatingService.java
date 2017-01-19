package com.crimson.service;

import com.crimson.dto.TvShowDTO;
import com.crimson.dto.UserDTO;
import com.crimson.model.Rating;

import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
public interface RatingService {
    void saveRating(Rating rating);

    void deleteRating(Rating rating);

    void updateRating(Rating rating);

    Rating getRatingByIdUser(Long idUser);

    Rating getRatingByID(Long id);

    Rating getRatingByIdTvShow(Long idTvShow);

    List<Rating> getAllRatings();

    Rating getR(long idtv, long iduser);
    void saveUserRating(UserDTO userDTO, TvShowDTO tvShowDTO, int value);
}
