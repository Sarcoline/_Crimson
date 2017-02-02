package com.crimson.core.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 20, message = "{invalid.size.genre}")
    @Pattern(regexp = "[A-z][a-z]+", message = "{invalid.pattern.genre}")
    private String name;

    //Genre2TvShow
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Genre2TvShow",
            joinColumns = @JoinColumn(name = "idGenre"),
            inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> genreTvShowList = new ArrayList<>();

    //Optimistic Locking
    @Version
    private int version;

    public Genre() {
    }

    public Genre(Builder builder) {
        name = builder.name;
    }

    public static class Builder{

        private String name;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Genre build(){return new Genre(this);}
    }


}