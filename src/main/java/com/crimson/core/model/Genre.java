package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "Genre")
public @Data class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenre")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 3, max = 20, message = "{invalid.size.genre}")
    @Pattern(regexp = "[A-Za-z]+", message = "{invalid.pattern.genre}")
    private String name;

    //Optimistic Locking
    @Version
    private int version;

    @Builder
    public Genre(String name, int version) {
        this.name = name;
        this.version = version;
    }

    //Genre2TvShow
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Genre2TvShow",
            joinColumns = @JoinColumn(name = "idGenre"),
            inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> tvShows = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Genre["+ id + "_" + name + "]";
    }
}