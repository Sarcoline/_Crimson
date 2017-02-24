package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;


public interface GenreDAO extends BaseDAO<Genre, Long> {

    Genre getGenreByName(String name);

    void addTvShow2Genre(Genre genre, TvShow tvShow);

    void deleteTvShowFromGenre(Genre genre, TvShow tvShow);
}
