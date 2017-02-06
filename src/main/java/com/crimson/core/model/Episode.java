package com.crimson.core.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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
    @Range(max = 99, message = "{invalid.season}")
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
    private int version;


    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(mappedBy = "userEpisodeList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> episodeUserList = new ArrayList<>();

    //TvShow2Episode Relation
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idTvShow", insertable = false, updatable = false)
    private TvShow episodeFromTvShow;

    public Episode() {
    }

    public Episode(Builder builder) {
        title = builder.title;
        season = builder.season;
        number = builder.number;
        releaseDate = builder.releaseDate;
        episodeSummary = builder.episodeSummary;
        idTvShow = builder.idTvShow;
    }

    public static class Builder{

        private String title;
        private int season;
        private int number;
        private Date releaseDate;
        private String episodeSummary;
        private Long idTvShow;

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder season(int season){
            this.season = season;
            return this;
        }

        public Builder number(int number){
            this.number = number;
            return this;
        }

        public Builder releaseDate(Date releaseDate){
        this.releaseDate = releaseDate;
        return this;
        }

        public Builder episodeSummary(String episodeSummary){
            this.episodeSummary = episodeSummary;
            return this;
        }

        public Builder idTvShow(Long idTvShow){
            this.idTvShow = idTvShow;
            return this;
        }

        public Episode build(){return new Episode(this);}
    }


}