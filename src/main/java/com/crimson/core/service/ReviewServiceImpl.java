package com.crimson.core.service;

import com.crimson.core.dao.ReviewDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.ReviewDTO;
import com.crimson.core.model.Review;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
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

    @Override
    public void save(ReviewDTO reviewDTO) {
        Review review = new Review(reviewDTO.getTitle(), reviewDTO.getIntroduction(), reviewDTO.getContent());
        addTvShow2Review(review, tvShowDAO.getTvBySlug(reviewDTO.getSlug()));
        addUser2Review(review, userDAO.getUserByName(reviewDTO.getUsername()));
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
    public void addTvShow2Review(Review review, TvShow tvShow) {
        if (review.getTvShow() != tvShow) {
            review.setTvShow(tvShow);
            reviewDAO.update(review);
        }
        if (!tvShowDAO.getReviews(tvShow).contains(review)) {
            List<Review> reviews = tvShowDAO.getReviews(tvShow);
            reviews.add(review);
            tvShow.setReviews(reviews);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public void addUser2Review(Review review, User user) {
        if (review.getUser() != user) {
            review.setUser(user);
            reviewDAO.update(review);
        }
        if (!userDAO.getReviews(user).contains(review)) {
            List<Review> reviews = userDAO.getReviews(user);
            reviews.add(review);
            user.setReviews(reviews);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteTvShowFromReview(Review review, TvShow tvShow) {
        if (review.getTvShow() == tvShow) {
            review.setTvShow(null);
            reviewDAO.update(review);
        }
        if (tvShowDAO.getReviews(tvShow).contains(review)) {
            List<Review> reviews = tvShowDAO.getReviews(tvShow);
            reviews.remove(review);
            tvShow.setReviews(reviews);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public void deleteUserFromReview(Review review, User user) {
        if (review.getUser() == user) {
            review.setUser(null);
            reviewDAO.update(review);
        }
        if (userDAO.getReviews(user).contains(review)) {
            List<Review> reviews = userDAO.getReviews(user);
            reviews.remove(review);
            user.setReviews(reviews);
            userDAO.update(user);
        }
    }

    @Override
    public List<Review> getReviews(long idTvShow, long idUser) {
        return reviewDAO.getReviews(idTvShow, idUser);
    }
}
