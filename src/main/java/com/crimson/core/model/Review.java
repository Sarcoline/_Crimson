package com.crimson.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "review")
public @Data class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReview")
    private Long id;

    @Column(name = "reviewTitle")
    @Length(min = 5,max = 100)
    @NotNull
    private String title;

    @Column(name = "reviewIntroduction")
    @Length(min = 5,max = 500)
    @NotNull
    private String introduction;

    @Column(name = "reviewContent")
    @Length(min = 5, max = 5000)
    @NotNull
    private String content;

    @Column(name = "reviewDate")
    @NotNull
    private LocalDate publicationDate;

    @Version
    private int version;

    @Builder
    public Review(String title, String introduction, String content){
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.publicationDate = LocalDate.now();
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
        return "Review["+ id + "_" + title + "]";
    }
}
