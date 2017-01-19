package com.crimson.service;

import com.crimson.dao.RatingDAO;
import com.crimson.dto.UserDTO;
import com.crimson.model.Rating;
import com.crimson.model.User;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDAO ratingDAO;
    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void saveRating(Rating rating){
        ratingDAO.saveRating(rating);
    }

    @Override
    public void deleteRating(Rating rating){
        ratingDAO.deleteRating(rating);
    }

    @Override
    public void updateRating(Rating rating){
        ratingDAO.updateRating(rating);
    }

    @Override
    public Rating getRatingByIdUser(Long idUser){
       return ratingDAO.getRatingByIdUser(idUser);
    }

    @Override
    public Rating getRatingByID(Long id){
        return ratingDAO.getRatingByID(id);
    }

    @Override
    public Rating getRatingByIdTvShow(Long idTvShow){
        return ratingDAO.getRatingByIdTvShow(idTvShow);
    }

    @Override
    public List<Rating> getAllRatings(){
      return ratingDAO.getAllRatings();
    }

    @Override
    public Rating getR(long idtv, long iduser) {
       return ratingDAO.getR(idtv,iduser);
    }

    public void saveUserRating(UserDTO userDTO, Rating rating) {
        User user = mapperFacade.map(userDTO, User.class);
        rating.setUserRating(user);
        ratingDAO.saveRating(rating);
    }
}
