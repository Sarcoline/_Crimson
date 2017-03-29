package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static org.apache.taglibs.standard.functions.Functions.substring;

@Entity
@NoArgsConstructor
@Table(name = "comment")
public @Data class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComment")
    private Long id;

    @Column(name = "commentText")
    @Length(min = 5,max = 500)
    @NotNull
    private String text;

    @Column(name = "commentDate")
    @NotNull
    private LocalDate date;

    @Version
    private int version;

    @Builder
    public Comment(String text){
        this.text = text;
        this.date = LocalDate.now();
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

    @Override
    public String toString()
    {
        return String.format("Comment[%d_%s...]", id, substring(text, 0, 10));
    }
}
