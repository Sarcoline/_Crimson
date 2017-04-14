package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class RatingDTO {

    @NotNull
    @Range(min = 0, max = 10, message = "{invalid.value}")
    private int value;
    private Long id;
    private TvShowSearchDTO tvShow;
}
