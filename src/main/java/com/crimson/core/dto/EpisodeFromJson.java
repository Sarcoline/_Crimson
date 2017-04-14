package com.crimson.core.dto;

import lombok.Data;

public @Data class EpisodeFromJson {

    private String title;
    private int season;
    private int number;
    private String summary;
    private String releaseDate;
    private long idTvShow;
    private long id;

    @Override
    public String toString()
    {
        return String.format("EpisodeFromJson[%d_%s]", id, title);
    }
}
