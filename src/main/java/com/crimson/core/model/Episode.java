package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "[A-Z][a-z]*(([ ]?[A-Za-z]+)?)*", message = "{invalid.pattern.title}")
    private String title;

    @Column(name = "season")
    @Pattern(regexp = "[A-Za-z1-9]*")
    private String season;

    @Column(name = "number")
    @Range(max = 99, message = "{invalid.number}")
    private int number;

    @Column(name = "releaseDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past(message = "{invalid.releaseDate}")
    private Date realeaseData;

    @Column(name = "episodeSummary")
    @Length(max = 1000)
    private String episodeSummary;

    @Column(name = "idTvShow")
    private Long idTvShow;

    //Optimistic Locking
    @Version
    private int version;

    @Builder
    public Episode(String title, String season, int number, Date realeaseData, String episodeSummary, Long idTvShow) {
        this.title = title;
        this.season = season;
        this.number = number;
        this.realeaseData = realeaseData;
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