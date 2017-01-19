package com.crimson.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;


@Data
@Entity
@Table(name = "Episode")
public class Episode {
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


    public Episode(Long id, String title, String season, int number, Date realeaseData, String episodeSummary){
        super();
        this.id = id;
        this.title = title;
        this.season = season;
        this.number = number;
        this.realeaseData = realeaseData;
        this.episodeSummary = episodeSummary;
    }

    public Episode(){
    }

    //RELATIONSHIPS

    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(mappedBy = "userEpisodeList")
    private List<User> episodeUserList = new ArrayList<>();

    public List<User> getEpisodeUserList(){return episodeUserList;}
    public void setEpisodeUserList(List<User> episodeUserList){this.episodeUserList = episodeUserList;}

    //TvShow2Episode Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTvShow")
    private TvShow episodeFromTvShow;

    public TvShow getEpisodeFromTvShow(){return episodeFromTvShow;}
    public void setEpisodeFromTvShow(TvShow episodeFromTvShow){this.episodeFromTvShow = episodeFromTvShow;}


}