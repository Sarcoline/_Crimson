package com.crimson.core.dao;

import com.crimson.core.model.*;

import java.util.List;

/**
 * Created by Meow on 30.01.2017.
 */
public interface TvShowDAO {
    void saveTvShow(TvShow tv);

    List<TvShow> getAllTvShows();

    TvShow getTvById(Long id);

    TvShow getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    void deleteTvShow(TvShow tvshow);

    void updateTvShow(TvShow tvshow);

    void addUser2TvShow(User user, TvShow tvShow);

    void deleteUserFromTvShow(User user, TvShow tvShow);

    void addGenre2TvShow(TvShow tvShow, Genre genre);

    void deleteGenreFromTvShow(TvShow tvShow, Genre genre);

    void addEpisode2TvShow(TvShow tvShow, Episode episode);

    void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode);

    void addRating2TvShow(TvShow tvShow, Rating rating);

    void deleteRatingFromTvShow(TvShow tvShow, Rating rating);
}