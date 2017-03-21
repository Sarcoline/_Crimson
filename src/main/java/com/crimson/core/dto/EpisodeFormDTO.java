package com.crimson.core.dto;

import com.crimson.core.model.TvShow;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public @Data class EpisodeFormDTO {

    private Long id;

    @Size(min = 3, max = 30)
    private String title;

    @Range(max = 99)
    private int season;

    @Range(max = 99)
    private int number;

    @NotNull
    private String releaseDate;

    @Length(max = 1000)
    private String episodeSummary;

    private Long idTvShow;

    private Integer version;

    private TvShow tvShow;

    @Override
    public String toString()
    {
        return "EpisodeFromDTO["+ id + "_" + title + "]";
    }
}
