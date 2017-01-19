package com.crimson.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Entity
@Table(name = "TvShow")
public class TvShow {

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
    @ManyToMany(mappedBy = "userTvShowList")
    private List<User> tvShowUserList = new ArrayList<>();

    public List<User> getTvShowUserList(){return tvShowUserList;}
    public void setTvShowUserList(List<User> tvShowUserList){this.tvShowUserList = tvShowUserList;}

    //TvShow2Genre
    @ManyToMany(mappedBy = "genreTvShowList")
    private List<Genre> tvShowGenreList = new ArrayList<>();

    public List<Genre> getTvShowGenreList(){return tvShowGenreList;}
    public void setTvShowGenreList(List<Genre> tvShowGenreList){this.tvShowGenreList = tvShowGenreList;}

    //TvShowEpisode Relation
    @OneToMany(mappedBy = "episodeFromTvShow")
    private List<Episode> episodes = new ArrayList<>();

    public List<Episode> getEpisodes(){return episodes;}
    public void setEpisodes(List<Episode> episodes){this.episodes = episodes;}

    //Rating
    @OneToMany(mappedBy = "tvShowRating")
    private List<Rating> tvShowRating = new ArrayList<>();

    public List<Rating> getTvShowRating(){return tvShowRating;}
    public void setTvShowRating(List<Rating> tvShowRating){this.tvShowRating = tvShowRating;}
}
