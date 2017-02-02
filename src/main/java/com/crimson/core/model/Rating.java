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
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idTvShow")
    private TvShow tvShowRating;

    public Rating(int value) {
        super();
        this.value = value;
    }

    public Rating() {
    }
}