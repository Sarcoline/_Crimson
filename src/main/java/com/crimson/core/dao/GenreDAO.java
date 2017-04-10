package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;

import java.util.List;


public interface GenreDAO extends BaseDAO<Genre, Long> {

    Genre getGenreByName(String name);

    List<TvShow> getTvShows(Genre genre);

    long GenreSize();
}
