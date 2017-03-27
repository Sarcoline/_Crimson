package com.crimson.core.dao;


import com.crimson.core.model.Review;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;

public interface ReviewDAO extends BaseDAO<Review, Long> {

    void addTvShow2Review(Review review, TvShow tvShow);

    void addUser2Review(Review review, User user);

    void deleteTvShowFromReview(Review review);

    void deleteUserFromReview(Review review);

    List<Review> getReviewByIdUser(Long idUser);

    List<Review> getReviewByIdTvShow(Long idTvShow);

    List getReviews(long idTvShow, long idUser);
}
