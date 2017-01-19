package com.crimson.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "role")
    private String role = "ROLE_USER";

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    @Lob
    private byte[] profilePic;

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



    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    //RELATIONSHIPS

    //User2TvShow
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User2TvShow",
    joinColumns = @JoinColumn(name = "idUser"),
    inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> userTvShowList = new ArrayList<>();

    public List<TvShow> getUserTvShowList(){return userTvShowList;}
    public void setUserTvShowList(List<TvShow> userTvShowList){this.userTvShowList = userTvShowList;}

    //User2Episode
    @ManyToMany
    @JoinTable(name = "EpisodeWatched",
    joinColumns = @JoinColumn(name = "idUser"),
    inverseJoinColumns = @JoinColumn(name = "idEpisode"))
    private List<Episode> userEpisodeList = new ArrayList<>();

    public List<Episode> getUserEpisodeList(){return userEpisodeList;}
    public void setUserEpisodeList(List<Episode> userEpisodeList){this.userEpisodeList = userEpisodeList;}

    //Rating
    @OneToMany(mappedBy = "userRating")
    private List<Rating> userRatings = new ArrayList<>();

    public  List<Rating> getUserRatings(){return userRatings;}
    public  void setUserRatings(List<Rating> userRatings){this.userRatings = userRatings;}
}