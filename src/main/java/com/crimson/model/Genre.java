package com.crimson.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenre")
    private Long id;

    @Column(name = "name")
    private String name;



    public Genre(Long id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public Genre(){

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    //Genre2TvShow
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Genre2TvShow",
    joinColumns = @JoinColumn(name = "idGenre"),
    inverseJoinColumns = @JoinColumn(name = "idTvShow"))
    private List<TvShow> genreTvShowList = new ArrayList<>();

    public List<TvShow> getGenreTvShowList(){return genreTvShowList;}
    public void setGenreTvShowList(List<TvShow> genreTvShowList){this.genreTvShowList = genreTvShowList;}

}