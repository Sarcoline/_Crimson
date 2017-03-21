package com.crimson.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

import static org.apache.taglibs.standard.functions.Functions.substring;

@NoArgsConstructor
public @Data class CommentDTO {

    private long id;

    public CommentDTO(String value, UserDTO user, TvShowDTO tvShow ) {
        this.text = value;
        this.tvShow = tvShow;
        this.user = user;
    }

    public CommentDTO(String value, long idTvShow) {
        this.text = value;
        this.idTvShow = idTvShow;
    }

    private long idTvShow;

    @Range(min = 10, max = 200)
    private String text;
    private TvShowDTO tvShow;
    private UserDTO user;
    private LocalDate date;

    @Override
    public String toString()
    {
        return "CommentDTO["+ id + "_" + substring(text,0,10) + "...]";
    }
}