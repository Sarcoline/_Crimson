package com.crimson.core.model;

import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;

import javax.persistence.*;
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
    private String title;

    @Column(name = "season")
    private String season;

    @Column(name = "number")
    private int number;

    @Column(name = "releaseDate")
    private Date realeaseData;

    @Column(name = "episodeSummary")
    private String episodeSummary;

    @Column(name = "idTvShow")
    private Long idTvShow;

    //Optimistic Locking
    @Version
    private Integer version;


    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(mappedBy = "userEpisodeList", cascade = CascadeType.ALL)
    private List<User> episodeUserList = new ArrayList<>();
    //TvShow2Episode Relation
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idTvShow", insertable = false, updatable = false)
    private TvShow episodeFromTvShow;

    public Episode() {
    }

    public Episode(Builder builder) {
        title = builder.title;
        season = builder.season;
        number = builder.number;
        realeaseData = builder.realeaseData;
        episodeSummary = builder.episodeSummary;
        idTvShow = builder.idTvShow;
    }

    public static class Builder{

        private String title;
        private String season;
        private int number;
        private Date realeaseData;
        private String episodeSummary;
        private Long idTvShow;

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder season(String season){
            this.season = season;
            return this;
        }

        public Builder number(int number){
            this.number = number;
            return this;
        }

        public Builder realeaseData(Date realeaseData){
        this.realeaseData = realeaseData;
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