package com.crimson.core.dto;

import lombok.Data;
import java.util.List;

/**
 * Created by adm on 2017-03-06.
 */
public @Data class FilterResponseDTO {
    int size;
    List<TvShowSearchDTO> tvShows;
}
