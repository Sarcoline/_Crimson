package com.crimson.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profilePicLocation")
    private String profilePicLocation;

    @Column(name = "role")
    private String role = "ROLE_USER";


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getProfilePicLocation() {return profilePicLocation;}
    public void setProfilePicLocation(String profilePicLocation) {
        this.profilePicLocation = profilePicLocation;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    //RELATIONSHIPS

    //User2TvShow Relation
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user2TvShow",
            joinColumns = {@JoinColumn(name = "idUser")},
            inverseJoinColumns = {@JoinColumn(name = "idTvShow")})
    public Set<TvShow> Users2TvShow = new HashSet<>();

    //EpisodeWatched(User2Episode) Relation
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EpisodeWatched",
    joinColumns = {@JoinColumn(name = "idUser")},
    inverseJoinColumns = {@JoinColumn(name = "idEpisode")})
    public Set<Episode> Users2Episode = new HashSet<>();


}