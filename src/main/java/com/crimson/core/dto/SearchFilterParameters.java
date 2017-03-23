package com.crimson.core.dto;

import lombok.Data;

public @Data class SearchFilterParameters {

    String genre;
    Integer releaseYearStart;
    Integer releaseYearEnd;
    String country;
    String network;
    Double minimalRating;
    Double maximumRating;

}
