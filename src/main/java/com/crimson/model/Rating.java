package com.crimson.model;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
public class Rating {

    @Column(name = "value")
    private int value;



    public Rating(int value){
        super();
        this.value = value;
    }

    public Rating(){

    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}