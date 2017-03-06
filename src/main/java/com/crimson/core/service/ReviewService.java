package com.crimson.core.service;

import com.crimson.core.dto.ReviewDTO;
import com.crimson.core.model.Review;
import java.util.List;

public interface ReviewService {

    void save(ReviewDTO reviewDTO);

    void delete(Review review);

    void update(Review review);

    Review getReviewById(Long idReview);

    List<Review> getAllReviews();

    @SuppressWarnings("unchecked")
    List<Review> getReviewByIdUser(Long idUser);

    @SuppressWarnings("unchecked")
    List<Review> getReviewByIdTvShow(Long idTvShow);

    List getReviews(long idTvShow, long idUser);
}
