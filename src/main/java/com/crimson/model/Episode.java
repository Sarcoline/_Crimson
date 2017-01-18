package com.crimson.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EpisodeWatched",
            joinColumns = {@JoinColumn(name = "idUser")},
            inverseJoinColumns = {@JoinColumn(name = "idEpisode")})
    public Set<User> Episode2Users = new HashSet<>();

}