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

    //Optimistic Locking
    @Version
    private int version;


    public TvShow(){

    }

    public TvShow(Builder builder){
        title = builder.title;
        network = builder.network;
        country = builder.country;
        genre = builder.genre;
        description = builder.description;
        trailerUrl = builder.trailerUrl;
        overallRating = builder.overallRating;
        releaseYear = builder.releaseYear;
        slug = builder.slug;
    }

    public static class Builder{

        private String title;
        private String network;
        private String country;
        private String genre;
        private String description;
        private String trailerUrl;
        private Double overallRating;
        private int releaseYear;
        private String slug;

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder network(String network){
            this.network = network;
            return this;
        }

        public Builder country(String country){
            this.country = country;
            return this;
        }

        public Builder genre(String genre){
            this.genre = genre;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder trailerUlr(String trailerUrl){
            this.trailerUrl = trailerUrl;
            return this;
        }

        public Builder overallRating(Double overallRating){
            this.overallRating = overallRating;
            return this;
        }

        public Builder releaseYear(int releaseYear){
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder slug(String slug){
            this.slug = slug;
            return this;
        }

        public TvShow build(){return new TvShow(this);}
    }

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
