package com.crimson.core.dao;

import com.crimson.core.dto.FilterResponse;
import com.crimson.core.dto.SearchFilterParameters;
import com.crimson.core.model.*;

import java.util.List;

public interface TvShowDAO extends BaseDAO<TvShow, Long> {

    TvShow getTvByIdWithEpisodes(Long id);

    TvShow getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    List<TvShow> getTvByCountry(String country);

    List<TvShow> getTvByYear(int releaseYear);

    List<TvShow> getTvByNetwork(String network);

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

    void addComment(TvShow tvShow, Comment comment);

    void addReview(TvShow tvShow, Review review);

    void deleteComment(TvShow tvShow, Comment comment);

    void deleteReview(TvShow tvShow, Review review);

    FilterResponse filter(SearchFilterParameters parameters, int page);

    List<User> getUsers(TvShow tv);

    List<Genre> getGenres(TvShow tv);

    List<Episode> getEpisodes(TvShow tv);

    List<Rating> getRatings(TvShow tv);

    List<Comment> getComments(TvShow tv);

    List<Review> getReviews(TvShow tv);
}
