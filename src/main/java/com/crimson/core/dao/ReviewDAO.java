package com.crimson.core.dao;


import com.crimson.core.model.Review;

import java.util.List;

public interface ReviewDAO extends BaseDAO<Review, Long> {

    @SuppressWarnings("unchecked")
    List<Review> getReviewByIdUser(Long idUser);

    @SuppressWarnings("unchecked")
    List<Review> getReviewByIdTvShow(Long idTvShow);

    List getReviews(long idTvShow, long idUser);
}
