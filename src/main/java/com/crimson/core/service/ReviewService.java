package com.crimson.core.service;

import com.crimson.core.dto.ReviewDTO;
import com.crimson.core.model.Review;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;

public interface ReviewService {

    void save(ReviewDTO reviewDTO);

    void delete(Review review);

    void update(Review review);

    Review getReviewById(Long idReview);

    List<Review> getAllReviews();

    List<Review> getReviewByIdUser(Long idUser);

    List<Review> getReviewByIdTvShow(Long idTvShow);

    void addTvShow2Review(Review review, TvShow tvShow);

    void addUser2Review(Review review, User user);

    void deleteTvShowFromReview(Review review, TvShow tvShow);

    void deleteUserFromReview(Review review, User user);

    List<Review> getReviews(long idTvShow, long idUser);
}
