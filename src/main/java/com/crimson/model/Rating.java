package com.crimson.model;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
public class Rating {

    @Column(name = "value")
    private int value;

    @Column(name = "idUser")
    private Long idUser;

    @Column(name = "idTvShow")
    private Long idTvShow;

    public Rating(int value, Long idUser, Long idTvShow){
        super();
        this.idUser = idUser;
        this.idTvShow = idTvShow;
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

    public Long getIdUser(){return idUser;}
    public void setIdUser(Long idUser){this.idUser = idUser;}

    public Long getIdTvShow(){return idTvShow;}
    public void setIdTvShow(Long idTvShow){this.idTvShow = idTvShow;}

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser", updatable = false, insertable = false)
    private User userRating = new User();

    public User getUserRating(){return userRating;}
    public void setUserRating(User userRating){this.userRating = userRating;}

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTvShow", updatable = false, insertable = false)
    private TvShow tvShowRating = new TvShow();

    public TvShow getTvShowRating(){return tvShowRating;}
    public void setTvShowRating(TvShow tvShowRating){this.tvShowRating = tvShowRating;}
}