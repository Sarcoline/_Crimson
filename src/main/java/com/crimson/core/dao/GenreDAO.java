package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;

import java.util.List;


public interface GenreDAO {
    void save(Genre genre);

    void delete(Genre genre);

    void update(Genre genre);

    Genre getGenreById(Long idGenre);

    List<Genre> getAllGenre();

    Genre getGenreByName(String name);

    void addTvShow2Genre(Genre genre, TvShow tvShow);

    void deleteTvShowFromGenre(Genre genre, TvShow tvShow);
}
