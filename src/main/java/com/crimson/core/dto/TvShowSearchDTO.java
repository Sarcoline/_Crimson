package com.crimson.core.dto;

import lombok.Data;


public @Data class TvShowSearchDTO {

    private Long id;

    private String title;

    private Double overallRating;

    private String slug;

    private int finishYear;
    @Override
    public String toString()
    {
        return String.format("TvShowSearchDTO[%d_%s]", id, slug);
    }
}
