package com.crimson.model;

import lombok.Data;

import javax.persistence.*;

@Data
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
}