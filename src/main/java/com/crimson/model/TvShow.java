package com.crimson.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TvShow")
public class TvShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTvShow")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "network")
    private String network;

    @Column(name = "country")
    private String country;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    @Lob
    @Length(max = 10000)
    private String description;

    @Column(name = "overallRating")
    private String trailerUrl;

    @Column(name = "trailerUrl")
    private Double overallRating;

    @Column(name = "releaseYear")
    private int releaseYear;

    @Column(name = "slug")
    private String slug;

    @Lob
    @Column(name = "picture")
    private HashMap<String, byte[]> pictures = new HashMap<>();


    //RELATIONSHIPS
    //User2TvShow Relation
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user2TvShow",
            joinColumns = {@JoinColumn(name = "idUser")},
            inverseJoinColumns = {@JoinColumn(name = "idTvShow")})
    public Set<User> TvShows2User = new HashSet<User>();


}
