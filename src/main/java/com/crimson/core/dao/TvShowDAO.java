package com.crimson.core.dao;

import com.crimson.core.model.*;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

public interface TvShowDAO extends BaseDAO<TvShow, Long> {

    Session getSession();

    TvShow getTvByIdWithEpisodes(Long id);

    TvShow getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    List<TvShow> getTvByCountry(String country);

    List<TvShow> getTvByYear(int releaseYear);

    List<TvShow> getTvByNetwork(String network);

    List<TvShow> searchTvShow(String pattern);

    List<TvShow> filterTvShows(double min, double max);

    long tvShowsSize();

    Long getTvShowsToPaginationByQuery();

    List<TvShow> queryGettingTvShowListForPage(int pageNumber, int maxResults);

    List<User> getUsers(TvShow tv);

    List<Genre> getGenres(TvShow tv);

    List<Episode> getEpisodes(TvShow tv);

    List<Rating> getRatings(TvShow tv);

    List<Comment> getComments(TvShow tv);

    List<Review> getReviews(TvShow tv);

    HashMap<String, byte[]> getTvShowPicture(String slug);

    List<TvShow> getTvShowsByMaxRating();
}
