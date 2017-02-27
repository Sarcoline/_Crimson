package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "comment")
public @Data class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComment")
    private Long id;

    @Column(name = "commentText")
    private String text;

    @Column(name = "commentDate")
    private LocalDate date;

    @Version
    private int version;

    @Builder
    public Comment(String text, LocalDate date, int version){
        this.text = text;
        this.date = date;
        this.version = version;
    }

    //RELATIONSHIPS

    //user
    @ManyToOne(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn(name = "idUser")
    private User user;

    //tvshow
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "idTvShow")
    private TvShow tvShow;
}
