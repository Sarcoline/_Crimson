package com.crimson.model;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
public class Rating {

    @Column(name = "value")
    private int value;

    public Rating(int value){
        super();
        this.value = value;
    }

    public Rating(){
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idUser")
    private User userRating;

    public User getUserRating(){return userRating;}
    public void setUserRating(User userRating){this.userRating = userRating;}

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idTvShow")
    private TvShow tvShowRating;

    public TvShow getTvShowRating(){return tvShowRating;}
    public void setTvShowRating(TvShow tvShowRating){this.tvShowRating = tvShowRating;}
}