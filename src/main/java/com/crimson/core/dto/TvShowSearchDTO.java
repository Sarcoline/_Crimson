package com.crimson.core.dto;

import lombok.Data;


public @Data class TvShowSearchDTO {

    private Long id;

    private String title;

    private Double overallRating;

    private String slug;
}
