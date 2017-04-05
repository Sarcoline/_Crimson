package com.crimson.core.dao;


import com.crimson.core.model.Review;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;

public interface ReviewDAO extends BaseDAO<Review, Long> {

    List<Review> getReviewByIdUser(Long idUser);

    List<Review> getReviewByIdTvShow(Long idTvShow);

    List<Review> getReviews(long idTvShow, long idUser);
}
