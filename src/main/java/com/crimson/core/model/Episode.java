package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Episode")
@Builder(builderMethodName = "hiddenBuilder", toBuilder = true)
public @Data class Episode {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "idEpisode")
            private Long id;

            @Column(name = "title")
            @Size(min = 3, max = 30, message = "{invalid.size.title}")
            //@Pattern(regexp = "[A-Z][a-z]*(([ ]?[A-Za-z]+)?)*", message = "{invalid.pattern.title}")
            private String title;

            @Column(name = "season")
            @Range(max = 99, message = "{invalid.season}")
            private int season;

            @Column(name = "number")
            @Range(max = 99, message = "{invalid.number}")
            private int number;

            @Column(name = "releaseDate")
            @Temporal(javax.persistence.TemporalType.DATE)
            private Date releaseDate;

            @Column(name = "episodeSummary")
            @Length(max = 1000)
            private String episodeSummary;

            @Column(name = "idTvShow")
            private Long idTvShow;

            //Optimistic Locking
            @Version
            private int version;


            //EpisodeWatched(User2Episode) Relation
            @ManyToMany(mappedBy = "userEpisodeList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
            @Singular("episodeUserList")
            private List<User> episodeUserList;

            //TvShow2Episode Relation
            @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
            @JoinColumn(name = "idTvShow", insertable = false, updatable = false)
            private TvShow episodeFromTvShow;

    public static EpisodeBuilder builder(String title) {
        return hiddenBuilder().title(title);
    }

}