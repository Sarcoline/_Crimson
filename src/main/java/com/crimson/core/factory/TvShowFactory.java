package com.crimson.core.factory;

import com.crimson.core.model.TvShow;

/**
 * Created by Meow on 02.02.2017.
 */
public class TvShowFactory {

    public TvShow getTvShow(String title) {


        TvShow tv = null;

        switch (title.toLowerCase()) {
            case "dr.house": {
                tv = new TvShow.Builder()
                        .title("Dr.House")
                        .network("Netflix")
                        .country("US")
                        .genre("Drama")
                        .build();
                break;
            }
            case "friends": {
                tv = new TvShow.Builder()
                        .title("Friends")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }

        }
        return tv;
    }
}