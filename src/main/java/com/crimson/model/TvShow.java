package com.crimson.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TvShow")
public class TvShow {

    private Long id;
    private String title;
    private String network;
    private String country;
    private String genre;
    private String description;
    private String trailerUrl;
    private Double overallRating;
    private int releaseYear;
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



    private List<User> users = new ArrayList<User>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTvShow")
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    @Column(name = "network")
    public String getNetwork(){
        return network;
    }
    public void setNetwork(String network){
        this.network = network;
    }

    @Column(name = "country")
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }

    @Column(name = "genre")
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }

    @Column(name = "description")
    public String getDescription(){
        return description;
    }
    public  void setDescription(String description){
        this.description = description;
    }

    @Column(name = "overallRating")
    public Double getOverallRating(){
        return overallRating;
    }
    public void setOverallRating(Double overallRating){
        this.overallRating = overallRating;
    }

    @Column(name = "trailerUrl")
    public String getTrailerUrl(){
        return trailerUrl;
    }
    public void setTrailerUrl(String trailerUrl){
        this.trailerUrl = trailerUrl;
    }

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "User2TvShow",
            joinColumns = {@JoinColumn(name = "idUser")},
            inverseJoinColumns = {@JoinColumn(name = "idTvShow")})
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
