package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Meow on 14.04.2017.
 */
@Data
public class ReviewDisplayDTO {

    private Long id;
    LocalDate publicationDate;
    String username;
    Long idTvShow;

    @Length(min = 5,max = 100, message = "Title must be between 5 and 100 characters")
    @NotNull
    String title;

    @Length(min = 5,max = 500,message = "Introduction must be between 5 and 100 characters")
    @NotNull
    String introduction;

    @Length(min = 5, max = 5000, message = "Review must be between 5 and 5000 characters")
    @NotNull
    String content;
}
