package com.crimson.core.service;

import com.crimson.core.dao.RatingDAO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDAO ratingDAO;
    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void saveRating(Rating rating) {
        ratingDAO.saveRating(rating);
    }

    @Override
    public void deleteRating(Rating rating) {
        ratingDAO.deleteRating(rating);
    }

    @Override
    public void updateRating(Rating rating) {
        ratingDAO.updateRating(rating);
    }

    @Override
    public Rating getRatingByIdUser(Long idUser) {
        return ratingDAO.getRatingByIdUser(idUser);
    }

    @Override
    public Rating getRatingByID(Long id) {
        return ratingDAO.getRatingByID(id);
    }

    @Override
    public Rating getRatingByIdTvShow(Long idTvShow) {
        return ratingDAO.getRatingByIdTvShow(idTvShow);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingDAO.getAllRatings();
    }

    @Override
    public Rating getRating(long idtv, long iduser) {
        return ratingDAO.getRating(idtv, iduser);
    }

    public void saveUserRating(UserDTO userDTO, TvShowDTO tvShowDTO, int value) {
        User user = mapperFacade.map(userDTO, User.class);
        TvShow tvShow = mapperFacade.map(tvShowDTO, TvShow.class);
        Rating rating = ratingDAO.getRating(tvShow.getId(), user.getId());
        rating.setValue(value);
        rating.setUserRating(user);
        rating.setTvShowRating(tvShow);
        ratingDAO.saveRating(rating);
    }
}
