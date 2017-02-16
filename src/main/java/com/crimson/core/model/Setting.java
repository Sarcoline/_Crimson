package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
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

    @Builder
    public Setting(Boolean episodesFromBegining, int numberOfEpisodesOnUserPage, int version){
        this.episodesFromBegining = episodesFromBegining;
        this.numberOfEpisodesOnUserPage = numberOfEpisodesOnUserPage;
        this.version = version;
    }

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user2Setting;

}
