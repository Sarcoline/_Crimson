package com.crimson.core.dto;

import com.crimson.core.model.TvShow;
import lombok.Data;
import java.util.List;

/**
 * Created by adm on 2017-03-06.
 */
public @Data class FilterResponse {

    int size;
    List<TvShow> tvShows;
}
