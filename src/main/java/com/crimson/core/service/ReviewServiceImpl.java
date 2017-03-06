package com.crimson.core.service;

import com.crimson.core.dao.ReviewDAO;
import com.crimson.core.model.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    public void save(Review review) {
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
