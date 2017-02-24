package com.crimson.core.dto;

import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public @Data class EpisodeDTO {

    private Long id;

    @Size(min = 3, max = 30, message = "{invalid.size.title}")
    private String title;

    @Range(max = 99, message = "{invalid.number}")
    private int season;

    @Range(max = 99, message = "{invalid.number}")
    private int number;

    private LocalDate releaseDate;

    @Length(max = 1000)
    private String episodeSummary;

    private Long idTvShow;

    private Integer version;

    private List<User> users = new ArrayList<>();

    private TvShow tvShow;
}
