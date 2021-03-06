package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Table(name = "Rating")
public @Data class Rating {

    @Column(name = "value")
    @NotNull
    @Range(min = 0, max = 10, message = "{invalid.value}")
    private int value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Optimistic Locking
    @Version
    private int version;

    //Builder method to create new object
    @Builder
    public Rating(int value, int version) {
        this.value = value;
        this.version = version;
    }

    //Relationships

    //user
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "idUser")
    private User user;

    //tvshow
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "idTvShow")
    private TvShow tvShow;

    @Override
    public String toString()
    {
        return String.format("Raiting[%d_val%d]", id, value);
    }
}