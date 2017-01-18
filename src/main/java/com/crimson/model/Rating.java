package com.crimson.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    private Long id;
    @Column(name = "value")
    private int value;

    public Rating(int value){
        super();
        this.value = value;
    }

    public Rating(){
    }
}