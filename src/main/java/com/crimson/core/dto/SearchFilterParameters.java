package com.crimson.core.dto;

import lombok.Data;

/**
 * Created by Meow on 06.03.2017.
 */
public @Data class SearchFilterParameters {

    String genre;
    Integer releaseYearStart;
    Integer releaseYearEnd;
    String country;
    String network;
    Double minimalRating;
    Double maximumRating;

}
