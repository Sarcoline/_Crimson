package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;

import java.util.List;

/**
 * Created by Meow on 30.01.2017.
 */
public interface GenreDAO {
    void addGenre(Genre genre);

    void deleteGenre(Genre genre);

    void updateGenre(Genre genre);

    Genre getGenreById(Long idGenre);

    List<Genre> getAllGenre();

    Genre getGenreByName(String name);

    void addTvShow2Genre(Genre genre, TvShow tvShow);

    void deleteTvShowFromGenre(Genre genre, TvShow tvShow);
}
