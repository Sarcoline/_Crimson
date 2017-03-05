package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "Episode")
public @Data class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEpisode")
    private Long id;

    @Column(name = "title")
    @NotNull
    @Size(min = 1, max = 100, message = "{invalid.size.title}")
    private String title;

    @Column(name = "season")
    @NotNull
    @Range(max = 99, message = "{invalid.number}")
    private int season;

    @Column(name = "number")
    @NotNull
    @Range(max = 99, message = "{invalid.number}")
    private int number;

    @Column(name = "releaseDate")
    private LocalDate releaseDate;

    @Column(name = "episodeSummary")
    @Length(max = 1000)
    private String episodeSummary;

    @Column(name = "idTvShow")
    private Long idTvShow;

    //Optimistic Locking
    @Version
    private Integer version;


    @Builder
    public Episode(String title, int season, int number, LocalDate releaseDate, String episodeSummary, Long idTvShow) {
        this.title = title;
        this.season = season;
        this.number = number;
        this.releaseDate = releaseDate;
        this.episodeSummary = episodeSummary;
        this.idTvShow = idTvShow;
    }

    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(mappedBy = "episodes", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    //TvShow2Episode Relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTvShow", insertable = false, updatable = false)
    private TvShow tvShow;

}