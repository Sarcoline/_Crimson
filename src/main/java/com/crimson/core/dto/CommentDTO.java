package com.crimson.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public @Data class CommentDTO {

    private long id;

    public CommentDTO(String value, UserDTO user, TvShowDTO tvShow ) {
        this.value = value;
        this.tvShow = tvShow;
        this.user = user;
        this.creationDate = LocalDate.now();
    }

    public CommentDTO(String value, long idTvShow) {
        this.value = value;
        this.idTvShow = idTvShow;
    }

    private long idTvShow;
    private String value;
    private TvShowDTO tvShow;
    private UserDTO user;
    private LocalDate creationDate;

}