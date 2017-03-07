package com.crimson.core.service;

import com.crimson.core.dao.ReviewDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.ReviewDTO;
import com.crimson.core.model.Review;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MapperFacade mapperFacade;
    @Override
    public void save(ReviewDTO reviewDTO) {
        Review review = new Review(reviewDTO.getTitle(),reviewDTO.getIntroduction(),reviewDTO.getContent());
        review.setTvShow(tvShowDAO.getById(reviewDTO.getTvShow().getId()));
        review.setUser(userDAO.getById(reviewDTO.getUser().getId()));
        reviewDAO.save(review);
    }

    @Override
    public void delete(Review review) {
        reviewDAO.delete(review);
    }

    @Override
    public void update(Review review) {
        reviewDAO.update(review);
    }

    @Override
    public Review getReviewById(Long idReview) {
        return reviewDAO.getById(idReview);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewDAO.getAll();
    }

    @Override
    public List<Review> getReviewByIdUser(Long idUser) {
        return reviewDAO.getReviewByIdUser(idUser);
    }

    @Override
    public List<Review> getReviewByIdTvShow(Long idTvShow) {
        return reviewDAO.getReviewByIdTvShow(idTvShow);
    }

    @Override
    public List getReviews(long idTvShow, long idUser) {
        return reviewDAO.getReviews(idTvShow,idUser);
    }
}
