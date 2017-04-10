package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Setting")
public @Data class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idSetting")
    private Long id;

    @Column(name = "sendEpisodeList")
    private Boolean sendEpisodeList;

    @Column(name = "numberOfEpisodesOnUserPage")
    @Range(max = 99, message = "{invalid.numberOfEpisodesOnUserPage}")
    private int numberOfEpisodesOnUserPage;

    @Column(name = "daysOfUpcomingEpisodes")
    @Range(max = 30, message = "{invalid.daysOfUpcomingEpisodes}")
    private int daysOfUpcomingEpisodes;

    //Optimistic Locking
    @Version
    int version;

    //Builder method to create new object
    @Builder
    public Setting(Boolean sendEpisodeList, int numberOfEpisodesOnUserPage, int daysOfUpcomingEpisodes){
        this.sendEpisodeList = sendEpisodeList;
        this.numberOfEpisodesOnUserPage = numberOfEpisodesOnUserPage;
        this.daysOfUpcomingEpisodes = daysOfUpcomingEpisodes;
    }


    //Relationships

    //User2Setting
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Override
    public String toString()
    {
        return String.format("Setting[%d]", id);
    }
}
