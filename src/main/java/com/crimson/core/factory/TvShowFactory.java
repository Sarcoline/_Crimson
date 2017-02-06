package com.crimson.core.factory;

import com.crimson.core.model.TvShow;


public class TvShowFactory {

    public TvShow getTvShow(String title) {


        TvShow tv = null;

        switch (title.toLowerCase()) {
            case "dr.house": {
                tv = TvShow.builder()
                        .title("Dr.House")
                        .network("Netflix")
                        .country("US")
                        .genre("Drama")
                        .build();
                break;
            }
            case "friends": {
                tv = TvShow.builder()
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
