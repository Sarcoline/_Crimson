package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "TvShow")
public @Data class TvShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTvShow")
    private Long id;

    @Column(name = "title")
    @Size(min = 3, max = 30, message = "{invalid.size.title}")
    @Pattern(regexp = "[A-Z][a-z]*(([ ]?[A-Za-z]+)?)*", message = "{invalid.pattern.title}")
    private String title;

    @Column(name = "network")
    @Size(min = 3, max = 30, message = "{invalid.size.network}")
    @Pattern(regexp = "[A-Za-z0-9]*(([ ]?[A-Za-z0-9]+)?)*", message = "{invalid.pattern.network}")
    private String network;

    @Column(name = "country")
    @Size(min = 3, max = 30, message = "{invalid.size.country}")
    @Pattern(regexp = "[A-z][a-z]*(([ ]?[A-Z][a-z]*)?)*", message = "{invalid.pattern.country}")
    private String country;

    @Column(name = "genre")
    @Size(min = 3, max = 20, message = "{invalid.size.genre}")
    @Pattern(regexp = "[A-z][a-z]+", message = "{invalid.pattern.genre}")
    private String genre;

    @Column(name = "description")
    @Lob
    @Length(max = 10000)
    private String description;

    @Column(name = "overallRating")
    @Range(min = 0, max = 10, message = "{invalid.overallRating}")
    private Double overallRating;

    @Column(name = "trailerUrl")
    @URL(message = "{invalid.trailerUrl}")
    private String trailerUrl;

    @Column(name = "releaseYear")
    @Range(min = 1920, max = 2017, message = "{invalid.releaseYear}")
    private int releaseYear;

    @Column(name = "slug")
    @Size(max = 20, message = "{invalid.size.slug}")
    //@Pattern(regexp = "[a-z]*", message = "{invalid.pattern.slug}")
    private String slug;

    @Lob
    @Column(name = "picture")
    private HashMap<String, byte[]> pictures = new HashMap<>();

    //Optimistic Locking
    @Version
    private int version;

    //RELATIONSHIPS
    //User2TvShow Relation
    @ManyToMany(mappedBy = "userTvShowList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> tvShowUserList = new ArrayList<>();
    //TvShow2Genre
    @ManyToMany(mappedBy = "genreTvShowList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Genre> tvShowGenreList = new ArrayList<>();
    //TvShowEpisode Relation
    @OneToMany(mappedBy = "episodeFromTvShow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Episode> episodes = new ArrayList<>();
    //Rating
    @OneToMany(mappedBy = "tvShowRating", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> tvShowRating = new ArrayList<>();

    @Builder
    public TvShow(String title, String network, String country, String genre, String description, String trailerUrl, double overallRating, int releaseYear, String slug){
        this.title = title;
        this.network = network;
        this.country = country;
        this.genre = genre;
        this.description = description;
        this.trailerUrl = trailerUrl;
        this.overallRating = overallRating;
        this.releaseYear = releaseYear;
        this.slug = slug;
    }

}
