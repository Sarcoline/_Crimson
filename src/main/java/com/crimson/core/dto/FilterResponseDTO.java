package com.crimson.core.dto;

import lombok.Data;

import java.util.List;

public @Data class FilterResponseDTO {
    int size;
    List<TvShowEmptyDTO> tvShows;
}
