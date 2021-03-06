package com.crimson.core.service;

import com.crimson.core.dao.GenreDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Override
    public void addGenre(Genre genre) {
        genreDAO.save(genre);
    }

    @Override
    public void deleteGenre(Genre genre) {
        genreDAO.delete(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDAO.update(genre);
    }

    @Override
    public Genre getGenreById(Long idGenre) {
        return genreDAO.getById(idGenre);
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreDAO.getAll();
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreDAO.getGenreByName(name);
    }

    //RELATIONSHIPS

    //Genre2TvShow

    @Override
    public void addTvShow2Genre(Genre genre, TvShow tvShow) {
        if (!genreDAO.getTvShows(genre).contains(tvShow)) {
            List<TvShow> tvShows = genreDAO.getTvShows(genre);
            tvShows.add(tvShow);
            genre.setTvShows(tvShows);
            genreDAO.update(genre);
        }
        if (!tvShowDAO.getGenres(tvShow).contains(genre)) {
            List<Genre> genres = tvShowDAO.getGenres(tvShow);
            genres.add(genre);
            tvShow.setGenres(genres);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public void deleteTvShowFromGenre(Genre genre, TvShow tvShow) {
        if (genreDAO.getTvShows(genre).contains(tvShow)) {
            List<TvShow> tvShows = genreDAO.getTvShows(genre);
            tvShows.remove(tvShow);
            genre.setTvShows(tvShows);
            genreDAO.update(genre);
        }
        if (tvShowDAO.getGenres(tvShow).contains(genre)) {
            List<Genre> genres = tvShowDAO.getGenres(tvShow);
            genres.remove(genre);
            tvShow.setGenres(genres);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public List<TvShow> getTvShows(Genre genre) {
        return genreDAO.getTvShows(genre);
    }
}
