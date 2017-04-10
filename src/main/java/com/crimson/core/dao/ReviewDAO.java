package com.crimson.core.dao;


import com.crimson.core.model.Review;

import java.util.List;

public interface ReviewDAO extends BaseDAO<Review, Long> {

    List<Review> getReviewByIdUser(Long idUser);

    List<Review> getReviewByIdTvShow(Long idTvShow);

    List<Review> getReviews(long idTvShow, long idUser);

    long ReviewsSize();

}
