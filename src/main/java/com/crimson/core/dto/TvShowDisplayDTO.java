package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meow on 14.04.2017.
 */
@Data
public class TvShowDisplayDTO {

    private Long id;

    @Size(min = 3, max = 30)
    private String title;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "\\w+([+]?([ ]?\\w+)?)*")
    private String network;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-z]+(([ ]?[A-Za-z]+)?)*")
    private String country;

    @Length(max = 10000)
    private String description;

    @URL
    private String trailerUrl;

    @Range(min = 0, max = 10)
    private Double overallRating;

    @Range(min = 1920, max = 2017)
    private int releaseYear;

    private int finishYear;

    private boolean forAdult;

    @Size(min = 3, max = 20)
    @Pattern(regexp = "[A-Za-z]+")
    private String genre;

    private String slug;

    private List<EpisodeFromJson> episodes = new ArrayList<>();

    private List<CommentDisplayDTO> comments = new ArrayList<>();

    private List<RatingDTO> ratings = new ArrayList<>();

    private List<ReviewDisplayDTO> reviews = new ArrayList<>();
}
