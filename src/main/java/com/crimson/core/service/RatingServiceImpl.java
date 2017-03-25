package com.crimson.core.service;

import com.crimson.core.dao.RatingDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDAO ratingDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void saveRating(Rating rating) {
        ratingDAO.save(rating);
    }

    @Override
    public void deleteRating(Rating rating) {
        ratingDAO.delete(rating);
    }

    @Override
    public void updateRating(Rating rating) {
        ratingDAO.update(rating);
    }

    @Override
    public Rating getRatingByIdUser(Long idUser) {
        return ratingDAO.getRatingByIdUser(idUser);
    }

    @Override
    public Rating getRatingByID(Long id) {
        return ratingDAO.getById(id);
    }

    @Override
    public List<Rating> getRatingByIdTvShow(Long idTvShow) {
        return ratingDAO.getRatingByIdTvShow(idTvShow);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingDAO.getAll();
    }

    @Override
    public Rating getRating(long idtv, long iduser) {
        return ratingDAO.getRating(idtv, iduser);
    }

    @Override
    public void addTvShow2Rating(Rating rating, TvShow tvShow) {
        if (rating.getTvShow() != tvShow) {
            ratingDAO.addTvShow2Rating(rating, tvShow);
        }
        if (!tvShowDAO.getRatings(tvShow).contains(rating)) {
            tvShowDAO.addRating2TvShow(tvShow, rating);
        }
    }

    @Override
    public void deleteTvShowFromRating(Rating rating, TvShow tvShow) {
        if (rating.getTvShow() == tvShow) {
            ratingDAO.deleteTvShowFromRating(rating);
        }
        if (tvShowDAO.getRatings(tvShow).contains(rating)) {
            tvShowDAO.deleteRatingFromTvShow(tvShow, rating);
        }
    }

    @Override
    public void addUser2Rating(Rating rating, User user) {
        if (rating.getUser() != user) {
            ratingDAO.addUser2Rating(rating, user);
        }
        if (!userDAO.getRatings(user).contains(rating)) {
            userDAO.addRating2User(user, rating);
        }
    }

    @Override
    public void deleteUserFromRating(Rating rating, User user) {
        if (rating.getUser() == user) {
            ratingDAO.deleteUserFromRating(rating);
        }
        if (userDAO.getRatings(user).contains(rating)) {
            userDAO.deleteRatingFromUser(user, rating);
        }
    }

    @Override
    public void saveUserRating(UserDTO userDTO, TvShowDTO tvShowDTO, int value) {
        User user = mapperFacade.map(userDTO, User.class);
        TvShow tvShow = mapperFacade.map(tvShowDTO, TvShow.class);
        Rating rating = new Rating();
        rating.setValue(value);
        addTvShow2Rating(rating, tvShow);
        addUser2Rating(rating, user);
        saveRating(rating);
        calculateRating(tvShow.getId());
    }

    public void calculateRating(long id) {
        TvShow tvShow = tvShowDAO.getById(id);
        List<Rating> ratings = tvShow.getRatings();
        double overall = ratings.stream().mapToDouble(Rating::getValue).average().getAsDouble();
        tvShow.setOverallRating(overall);
        tvShowDAO.update(tvShow);
    }
}
