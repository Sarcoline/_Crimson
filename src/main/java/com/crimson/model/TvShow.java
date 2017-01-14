package com.crimson.model;

import javax.persistence.*;
import java.util.*;

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
    private String description;

    @Column(name = "overallRating")
    private String trailerUrl;

    @Column(name = "trailerUrl")
    private Double overallRating;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "slug")
    private String slug;


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



}
