package com.crimson.core.dto;

import lombok.Data;

public @Data class EpisodeFromJson {

    private String title;
    private int season;
    private int episode;
    private String summary;
    private String releaseDate;
    private long idTvShow;
    private long id;

    @Override
    public String toString()
    {
        return "EpisodeFromJson["+ id + "_" + title + "]";
    }
}
