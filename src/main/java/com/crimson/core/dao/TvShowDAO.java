package com.crimson.core.dao;

import com.crimson.core.model.*;

import java.util.List;

public interface TvShowDAO {
    void save(TvShow tv);

    List<TvShow> getAllTvShows();

    TvShow getTvById(Long id);

    TvShow getTvByIdWithEpisodes(Long id);

    TvShow getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    List<TvShow> getTvByCountry(String country);

    List<TvShow> getTvByYear(int releaseYear);

    List<TvShow> getTvByNetwork(String network);

    List<TvShow> getAllTvShowByMaxRating();

    void delete(TvShow tvshow);

    void update(TvShow tvshow);

    List<TvShow> searchTvShow(String pattern);

    List<TvShow> filterTvShows(double min, double max);

    long tvShowsSize();

    int tvShowsLastPageNumber();

    List<TvShow> tvShowsPaginationList(int pageNumber);

    void addUser2TvShow(User user, TvShow tvShow);

    void deleteUserFromTvShow(User user, TvShow tvShow);

    void addGenre2TvShow(TvShow tvShow, Genre genre);

    void deleteGenreFromTvShow(TvShow tvShow, Genre genre);

    void addEpisode2TvShow(TvShow tvShow, Episode episode);

    void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode);

    void addRating2TvShow(TvShow tvShow, Rating rating);

    void deleteRatingFromTvShow(TvShow tvShow, Rating rating);
}
