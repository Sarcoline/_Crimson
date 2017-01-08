package com.crimson.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "User")
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String profilePicLocation;

    private List<TvShow> tvshows = new ArrayList<TvShow>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "profilePicLocation")
    public String getProfilePicLocation() {
        return profilePicLocation;
    }
    public void setProfilePicLocation(String profilePicLocation) {
        this.profilePicLocation = profilePicLocation;
    }

    @ManyToMany(targetEntity = TvShow.class)
    @JoinTable(name = "User2TvShow",
            joinColumns = {@JoinColumn(name = "idUser")},
            inverseJoinColumns = {@JoinColumn(name = "idTvShow")})
    public List<TvShow> getTvShows() {
        return tvshows;
    }
    public void setTvShows(List<TvShow> tvshows) {
        this.tvshows = tvshows;
    }

}