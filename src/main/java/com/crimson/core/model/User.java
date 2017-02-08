package com.crimson.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Email;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 30, message = "{invalid.size.name}")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "{invalid.pattern.name}")
    private String name;

    @Column(name = "email")
    @Email(message = "{invalid.email}")
    private String email;

    @Column(name = "password")
    @Size(min = 3, max = 100, message = "{invalid.password}")
    private String password;

    @Column(name = "role")
    private String role = "ROLE_USER";

    @Lob
    private byte[] profilePic;

    //Optimistic Locking
    @Version
    private int version;

    //User2TvShow
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User2TvShow",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> userTvShowList = new ArrayList<>();
    //User2Episode
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EpisodeWatched",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idEpisode"))
    private List<Episode> userEpisodeList = new ArrayList<>();
    //Rating
    @OneToMany(mappedBy = "userRating", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> userRatings = new ArrayList<>();



    @Builder
    public User(String name, String email, String password, String role, byte[] profilePic, int version){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePic = profilePic;
        this.version = version;
    }

}