package com.crimson.core.dto;

import com.crimson.core.model.Episode;
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

    private List<Episode> episodes = new ArrayList<>();

    private HashMap<String, byte[]> pictures = new HashMap<>();
}
