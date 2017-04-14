package com.crimson.core.dto;

import lombok.Data;

import java.util.List;


public @Data class TvShowSearchDTO {

    private Long id;

    private String title;

    private Double overallRating;

    private String slug;

    private List<EpisodeFromJson> episodes;

    private int finishYear;
    @Override
    public String toString()
    {
        return String.format("TvShowSearchDTO[%d_%s]", id, slug);
    }
}
