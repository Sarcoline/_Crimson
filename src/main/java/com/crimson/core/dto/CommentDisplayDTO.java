package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

/**
 * Created by Meow on 14.04.2017.
 */
@Data
public class CommentDisplayDTO {

    private Long id;
    private long idTvShow;
    @Range(min = 10, max = 200)
    private String text;
    private String username;
    private LocalDate date;
}
