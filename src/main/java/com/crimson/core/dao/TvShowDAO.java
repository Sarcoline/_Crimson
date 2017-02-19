package com.crimson.core.dao;

import com.crimson.core.model.*;

import java.util.List;

public interface TvShowDAO {
    void saveTvShow(TvShow tv);

    List<TvShow> getAllTvShows();

    TvShow getTvById(Long id);

    TvShow getTvByIdWithEpisodes(Long id);

    TvShow getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    List<TvShow> getAllTvShowByMaxRating();

    void deleteTvShow(TvShow tvshow);

    void updateTvShow(TvShow tvshow);

    List<TvShow> searchTvShow(String pattern);

    List<TvShow> filterTvShows(double min, double max);

    List<TvShow> getTvShowsSortedByNumberOnList(int userChoosedNumberOnList, int pageNumber);

    void addUser2TvShow(User user, TvShow tvShow);

    void deleteUserFromTvShow(User user, TvShow tvShow);

    void addGenre2TvShow(TvShow tvShow, Genre genre);

    void deleteGenreFromTvShow(TvShow tvShow, Genre genre);

    void addEpisode2TvShow(TvShow tvShow, Episode episode);

    void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode);

    void addRating2TvShow(TvShow tvShow, Rating rating);

    void deleteRatingFromTvShow(TvShow tvShow, Rating rating);
}
