package com.crimson.core.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public @Data class TvShowAddDTO {

    @Size(min = 3, max = 30)
    private String title;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "[A-Za-z0-9]*(([ ]?[A-Za-z0-9]+)?)*")
    private String network;

    @Size(min = 3, max = 30)
    @Pattern(regexp = "[A-z][a-z]*(([ ]?[A-Z][a-z]*)?)*")
    private String country;

    @Length(max = 10000)
    private String description;

    @URL
    private String trailerUrl;

    @Range(min = 0, max = 10)
    private Double overallRating;

    @Range(min = 1920, max = 2017)
    private int releaseYear;

    private int finishYear;

    @Size(min = 3, max = 20)
    @Pattern(regexp = "[A-z][a-z]+")
    private String genre;

    private String slug;

    private MultipartFile back;

    private MultipartFile poster;

    private MultipartFile pic1;

    private MultipartFile pic2;

    private MultipartFile pic3;

    @Override
    public String toString()
    {
        return "TvShowAddDTO[new_" + slug + "]";
    }
}
