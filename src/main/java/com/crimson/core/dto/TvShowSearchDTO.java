package com.crimson.core.dto;

import lombok.Data;

/**
 * Created by Meow on 18.02.2017.
 */
public @Data class TvShowSearchDTO {

    private Long id;

    private String title;

    private Double overallRating;

    private String slug;
}
