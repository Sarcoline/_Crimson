package com.crimson.core.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private List<EpisodeDTO> episodes = new ArrayList<>();

    private HashMap<String, byte[]> pictures = new HashMap<>();
}
