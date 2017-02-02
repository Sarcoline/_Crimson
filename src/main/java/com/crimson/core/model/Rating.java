package com.crimson.core.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
public @Data class Rating {

    @Column(name = "value")
    private int value;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "idUser")
    private User userRating;
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "idTvShow")
    private TvShow tvShowRating;

    //Optimistic Locking
    @Version
    private int version;

    public Rating() {
    }

    public Rating(Builder builder) {
        value = builder.value;
    }



    public static class Builder{

        private int value;

        public Builder value(int value){
            this.value = value;
            return this;
        }

        public Rating build(){return new Rating(this);}
    }
}