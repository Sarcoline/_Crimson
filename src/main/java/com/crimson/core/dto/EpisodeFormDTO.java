package com.crimson.core.dto;

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

    @Length(max = 2000)
    private String episodeSummary;

    private Long idTvShow;

    private Integer version;

    private String slug;

    @Override
    public String toString()
    {
        return String.format("EpisodeFromDTO[%d_%s]", id, title);
    }
}
