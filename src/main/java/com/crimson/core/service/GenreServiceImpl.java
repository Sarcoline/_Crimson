package com.crimson.core.service;

import com.crimson.core.dao.GenreDAO;
import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDAO genreDAO;

    @Override
    public void addGenre(Genre genre) {
        genreDAO.addGenre(genre);
    }

    @Override
    public void deleteGenre(Genre genre) {
        genreDAO.deleteGenre(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDAO.updateGenre(genre);
    }

    @Override
    public Genre getGenreById(Long idGenre) {
        return genreDAO.getGenreById(idGenre);
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreDAO.getAllGenre();
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreDAO.getGenreByName(name);
    }

    //RELATIONSHIPS

    //Genre2TvShow

    @Override
    public void addTvShow2Genre(Genre genre, TvShow tvShow) {
        genreDAO.addTvShow2Genre(genre, tvShow);
    }

    @Override
    public void deleteTvShowFromGenre(Genre genre, TvShow tvShow) {
        genreDAO.deleteTvShowFromGenre(genre, tvShow);
    }
}
