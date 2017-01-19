package com.crimson.model;

import javassist.bytecode.ByteArray;
import org.hibernate.type.descriptor.java.ByteArrayTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarbinaryTypeDescriptor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "picture")
    private Byte picture;



    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getNetwork(){
        return network;
    }
    public void setNetwork(String network){
        this.network = network;
    }

    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }

    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getDescription(){
        return description;
    }
    public  void setDescription(String description){
        this.description = description;
    }

    public Double getOverallRating(){
        return overallRating;
    }
    public void setOverallRating(double overallRating){
        this.overallRating = overallRating;
    }

    public String getTrailerUrl(){
        return trailerUrl;
    }
    public void setTrailerUrl(String trailerUrl){
        this.trailerUrl = trailerUrl;
    }

    public Byte getPicture(){return picture;}
    public void setPicture(Byte picture){this.picture = picture;}

    //RELATIONSHIPS

    //User2TvShow Relation
    @ManyToMany(mappedBy = "userTvShowList", cascade = CascadeType.ALL)
    private List<User> tvShowUserList = new ArrayList<>();

    public List<User> getTvShowUserList(){return tvShowUserList;}
    public void setTvShowUserList(List<User> tvShowUserList){this.tvShowUserList = tvShowUserList;}

    //TvShow2Genre
    @ManyToMany(mappedBy = "genreTvShowList", cascade = CascadeType.ALL)
    private List<Genre> tvShowGenreList = new ArrayList<>();

    public List<Genre> getTvShowGenreList(){return tvShowGenreList;}
    public void setTvShowGenreList(List<Genre> tvShowGenreList){this.tvShowGenreList = tvShowGenreList;}

    //TvShowEpisode Relation
    @OneToMany(mappedBy = "episodeFromTvShow", cascade = CascadeType.ALL)
    private List<Episode> episodes = new ArrayList<>();

    public List<Episode> getEpisodes(){return episodes;}
    public void setEpisodes(List<Episode> episodes){this.episodes = episodes;}

    //Rating
    @OneToMany(mappedBy = "tvShowRating", cascade = CascadeType.ALL)
    private List<Rating> tvShowRating = new ArrayList<>();

    public List<Rating> getTvShowRating(){return tvShowRating;}
    public void setTvShowRating(List<Rating> tvShowRating){this.tvShowRating = tvShowRating;}
}
