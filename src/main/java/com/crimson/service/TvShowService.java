package com.crimson.service;

import com.crimson.model.*;

import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
public interface TvShowService {
    void saveTvShow(TvShow tvShow);

    List<TvShow> getAllTvShows();

    TvShow getTvById(Long id);

    TvShow getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    void deleteTvShow(TvShow tvshow);

    void updateTvShow(TvShow tvshow);

    TvShow getTvShowBySlug(String slug);

    void addUser2TvShow(User user, TvShow tvShow);

    void deleteUserFromTvShow(User user, TvShow tvShow);

    void addGenre2TvShow(TvShow tvShow, Genre genre);

    void deleteGenreFromTvShow(TvShow tvShow, Genre genre);

    void addEpisode2TvShow(TvShow tvShow, Episode episode);

    void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode);

    void addRating2TvShow(TvShow tvShow, Rating rating);

    void deleteRatingFromTvShow(TvShow tvShow, Rating rating);
}
