package com.crimson.dto;

import com.crimson.model.Episode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
@Data
public class TvShowDTO {

    private Long id;

    private String title;

    private String network;

    private String country;

    private String description;

    private String trailerUrl;

    private Double overallRating;

    private int releaseYear;

    private String genre;

    private String slug;

    private List<Episode> episodes = new ArrayList<>();
}
