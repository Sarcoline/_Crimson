package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
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
    @Size(min = 3, max = 30, message = "{invalid.size.title}")
    //@Pattern(regexp = "[A-Z][a-z]*(([ ]?[A-Za-z]+)?)*", message = "{invalid.pattern.title}")
    private String title;

    @Column(name = "season")
    @Range(max = 99, message = "{invalid.number}")
    //@Pattern(regexp = "[A-Za-z0-9]*", message = "{invalid.pattern.season}")
    private int season;

    @Column(name = "number")
    @Range(max = 99, message = "{invalid.number}")
    private int number;

    @Column(name = "releaseDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date releaseDate;

    @Column(name = "episodeSummary")
    @Length(max = 1000)
    private String episodeSummary;

    @Column(name = "idTvShow")
    private Long idTvShow;

    //Optimistic Locking
    @Version
    private Integer version;


    @Builder
    public Episode(String title, int season, int number, Date releaseDate, String episodeSummary, Long idTvShow) {
        this.title = title;
        this.season = season;
        this.number = number;
        this.releaseDate = releaseDate;
        this.episodeSummary = episodeSummary;
        this.idTvShow = idTvShow;
    }

    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(mappedBy = "userEpisodeList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> episodeUserList = new ArrayList<>();

    //TvShow2Episode Relation
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idTvShow", insertable = false, updatable = false)
    private TvShow episodeFromTvShow;

}