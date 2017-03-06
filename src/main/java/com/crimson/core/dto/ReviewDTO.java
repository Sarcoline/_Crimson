package com.crimson.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by Meow on 05.03.2017.
 */
@NoArgsConstructor
public @Data class ReviewDTO {

    LocalDate publicationDate;
    UserDTO author;
    TvShowDTO tvShow;
    String title;
    String introduction;
    String content;

}
