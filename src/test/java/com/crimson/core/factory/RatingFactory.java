package com.crimson.core.factory;

import com.crimson.core.model.Rating;


public class RatingFactory {

    public Rating getRating(int value) {
        Rating rating = null;

        switch (value) {
            case 5: {
                rating = Rating.builder()
                        .value(5)
                        .build();
                break;
            }

        }
        return rating;
    }
}
