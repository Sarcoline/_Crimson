package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "User")
public @Data class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long id;

    @Column(name = "name", unique = true)
    @Size(min = 3, max = 30, message = "{invalid.size.name}")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "{invalid.pattern.name}")
    private String name;

    @Column(name = "email")
    @Email(message = "{invalid.email}")
    private String email;

    @Column(name = "password")
    @Size(min = 3, max = 100, message = "{invalid.password}")
    private String password;

    @Lob
    private byte[] profilePic;

    //Optimistic Locking
    @Version
    private int version;

    @Builder
    public User(String name, String email, String password, byte[] profilePic, int version){
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.version = version;
    }


    //User2TvShow
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User2TvShow",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> tvShows = new ArrayList<>();
    //User2Episode
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EpisodeWatched",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEpisode"))
    private List<Episode> episodes = new ArrayList<>();

    //Rating
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<>();

    //User2Setting
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Setting setting;

    //User2Role
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User2Role",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRole"))
    private List<Role> roles = new ArrayList<>();

    //Comments
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


}