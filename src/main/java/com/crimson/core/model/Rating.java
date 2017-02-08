package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@NoArgsConstructor
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

    @Builder
    public Rating(int value, int version) {
        this.value = value;
        this.version = version;
    }
}