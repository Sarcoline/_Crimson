package com.crimson.core.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
public @Data class Rating {

    @Column(name = "value")
    @Range(min = 0, max = 10, message = "{invalid.value}")
    private int value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //user
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "idUser")
    private User userRating;

    //tvshow
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