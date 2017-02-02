package com.crimson.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Genre")
public @Data class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenre")
    private Long id;

    @Column(name = "name")
    private String name;
    //Genre2TvShow
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Genre2TvShow",
            joinColumns = @JoinColumn(name = "idGenre"),
            inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> genreTvShowList = new ArrayList<>();

    public Genre(String name) {
        super();
        this.name = name;
    }

    public Genre() {
    }
}