package com.crimson.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "User")
public @Data class User {

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

    @Column(name = "role")
    private String role = "ROLE_USER";
    @Lob
    private byte[] profilePic;

    public User(){

    }

    public User(Builder builder){
        name = builder.name;
        email = builder.email;
        password = builder.password;
        role = builder.role;
    }

    public static class Builder{

        private Long id;
        private String name;
        private String email;
        private String password;
        private String role;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder role(String role){
            this.role = "ROLE_USER";
            return this;
        }

        public User build(){
            return new User(this);
        }
    }




    //User2TvShow
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User2TvShow",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> userTvShowList = new ArrayList<>();
    //User2Episode
    @ManyToMany
    @JoinTable(name = "EpisodeWatched",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEpisode"))
    private List<Episode> userEpisodeList = new ArrayList<>();
    //Rating
    @OneToMany(mappedBy = "userRating")
    private List<Rating> userRatings = new ArrayList<>();

}