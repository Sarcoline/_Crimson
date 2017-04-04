package com.crimson.core.factory;

import com.crimson.core.model.Review;

public class ReviewFactory {
    public Review getReview(String name) {

        Review review = null;

        switch (name.toLowerCase()) {
            case "review1": {
                review = Review.builder()
                        .title("Unikaj!")
                        .introduction("Nie warto oglądać")
                        .content("Ten serial jest beznadziejny")
                        .build();
                break;
            }

            case "review2": {
                review = Review.builder()
                        .title("Warto!")
                        .introduction("obejrzyj jak tylko będziesz miał chwilę.")
                        .content("Ten serial jest super")
                        .build();
                break;
            }
        }
        return review;
    }
}
