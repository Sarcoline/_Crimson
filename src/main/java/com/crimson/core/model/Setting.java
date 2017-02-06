package com.crimson.core.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Setting")
public @Data class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idSetting")
    private Long id;

    @Column(name = "episodesFromBegining")
    private Boolean episodesFromBegining;

    @Column(name = "numberOfEpisodesOnUserPage")
    private int numberOfEpisodesOnUserPage;

    @Version
    int version;

    public Setting(){

    }

    @OneToOne
    @PrimaryKeyJoinColumn
    private User userSetting;

}
