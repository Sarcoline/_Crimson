package com.crimson.service;

import com.crimson.model.Genre;
import com.crimson.model.TvShow;

import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
public interface GenreService {
    void addGenre(Genre genre);

    void  deleteGenre(Genre genre);

    void updateGenre(Genre genre);

    Genre getGenreById(Long idGenre);

    List<Genre> getAllGenre();

    Genre getGenreByName(String name);

    void addTvShow2Genre(Genre genre, TvShow tvShow);

    void deleteTvShowFromGenre(Genre genre, TvShow tvShow);
}
