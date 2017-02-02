package com.crimson.core.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "TvShow")
public @Data class TvShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTvShow")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "network")
    private String network;

    @Column(name = "country")
    private String country;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    @Lob
    @Length(max = 10000)
    private String description;

    @Column(name = "overallRating")
    private String trailerUrl;

    @Column(name = "trailerUrl")
    private Double overallRating;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "slug")
    private String slug;

    @Lob
    @Column(name = "picture")
    private HashMap<String, byte[]> pictures = new HashMap<>();

    //RELATIONSHIPS
    //User2TvShow Relation
    @ManyToMany(mappedBy = "userTvShowList", cascade = CascadeType.ALL)
    private List<User> tvShowUserList = new ArrayList<>();
    //TvShow2Genre
    @ManyToMany(mappedBy = "genreTvShowList", cascade = CascadeType.ALL)
    private List<Genre> tvShowGenreList = new ArrayList<>();
    //TvShowEpisode Relation
    @OneToMany(mappedBy = "episodeFromTvShow", cascade = CascadeType.ALL)
    private List<Episode> episodes = new ArrayList<>();
    //Rating
    @OneToMany(mappedBy = "tvShowRating", cascade = CascadeType.ALL)
    private List<Rating> tvShowRating = new ArrayList<>();

}
