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

    @Column(name = "idTvShow")
    private Long idTvShow;


    public Episode(Long id, String title, String season, int number, Date realeaseData, String episodeSummary, Long idTvShow){
        super();
        this.id = id;
        this.title = title;
        this.season = season;
        this.number = number;
        this.realeaseData = realeaseData;
        this.episodeSummary = episodeSummary;
        this.idTvShow = idTvShow;
    }

    public Episode(){
    }

    public Long getIdTvShow(){return idTvShow;}
    public void setIdTvShow(Long idTvShow){this.idTvShow = idTvShow;}

    //RELATIONSHIPS

    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(mappedBy = "userEpisodeList", cascade = CascadeType.ALL)
    private List<User> episodeUserList = new ArrayList<>();

    public List<User> getEpisodeUserList(){return episodeUserList;}
    public void setEpisodeUserList(List<User> episodeUserList){this.episodeUserList = episodeUserList;}

    //TvShow2Episode Relation
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idTvShow", insertable = false, updatable = false)
    private TvShow episodeFromTvShow = new TvShow();

    public TvShow getEpisodeFromTvShow(){return episodeFromTvShow;}
    public void setEpisodeFromTvShow(TvShow episodeFromTvShow){this.episodeFromTvShow = episodeFromTvShow;}


}