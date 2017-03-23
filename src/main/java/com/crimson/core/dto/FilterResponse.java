package com.crimson.core.dto;

import com.crimson.core.model.TvShow;
import lombok.Data;

import java.util.List;

public @Data class FilterResponse {

    int size;
    List<TvShow> tvShows;
}
