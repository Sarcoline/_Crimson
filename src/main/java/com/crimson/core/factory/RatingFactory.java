package com.crimson.core.factory;

import com.crimson.core.model.Rating;

/**
 * Created by Meow on 02.02.2017.
 */
public class RatingFactory {

    public Rating getRating(int value){
        Rating rating = null;

        switch (value) {
            case 5: {
                rating = new Rating.Builder()
                        .value(5)
                        .build();
                break;
            }

        }
        return rating;
    }
}
