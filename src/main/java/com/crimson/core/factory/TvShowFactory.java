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

            case "test1": {
                tv = TvShow.builder()
                        .title("Test1")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }

            case "test2": {
                tv = TvShow.builder()
                        .title("Test2")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }

            case "test3": {
                tv = TvShow.builder()
                        .title("Test3")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }

            case "test4": {
                tv = TvShow.builder()
                        .title("Test4")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }
            case "test5": {
                tv = TvShow.builder()
                        .title("Test5")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }
            case "test6": {
                tv = TvShow.builder()
                        .title("Test6")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }
            case "test7": {
                tv = TvShow.builder()
                        .title("Test7")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }
            case "test8": {
                tv = TvShow.builder()
                        .title("Test8")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }
            case "test9": {
                tv = TvShow.builder()
                        .title("Test9")
                        .network("Network")
                        .country("US")
                        .genre("Comedy")
                        .build();
                break;
            }
            case "test10": {
                tv = TvShow.builder()
                        .title("Test10")
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
