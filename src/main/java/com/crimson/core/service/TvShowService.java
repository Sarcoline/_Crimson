package com.crimson.core.service;

import com.crimson.core.dto.*;
import com.crimson.core.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TvShowService {
    void saveTvShow(TvShow tvShow);

    List<TvShow> getAllTvShows();

    TvShowDisplayDTO getDisplayBySlug(String slug);

    TvShowDTO getTvById(Long id);

    TvShowDTO getTvByIdWithEpisodes(Long id);

    TvShowDTO getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    List<TvShow> getTvByCountry(String country);

    List<TvShow> getTvByYear(int releaseYear);

    List<TvShow> getTvByNetwork(String network);

    byte[] getTvPics(String slug, String name);

    void deleteTvShow(String name);

    void updateTvShow(TvShowDTO tvshow);

    void addUser2TvShow(User user, TvShow tvShow);

    void deleteUserFromTvShow(User user, TvShow tvShow);

    void addGenre2TvShow(TvShow tvShow, Genre genre);

    void deleteGenreFromTvShow(TvShow tvShow, Genre genre);

    void addEpisode2TvShow(TvShow tvShow, Episode episode);

    void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode);

    void addRating2TvShow(TvShow tvShow, Rating rating);

    ImageDTO getTvPictures(String slug);

    void deleteRatingFromTvShow(TvShow tvShow, Rating rating);

    void addComment(TvShow tvShow, Comment comment);

    void addReview(TvShow tvShow, Review review);

    void deleteComment(TvShow tvShow, Comment comment);

    void deleteReview(TvShow tvShow, Review review);

    List<User> getUsers(TvShow tv);

    List<Genre> getGenres(TvShow tv);

    List<Episode> getEpisodes(TvShow tv);

    List<Rating> getRatings(TvShow tv);

    List<Comment> getComments(TvShow tv);

    List<Review> getReviews(TvShow tv);

    List<TvShowSearchDTO> getAllTvShowByMaxRating();

    List<TvShowSearchDTO> searchTvShow(String pattern);

    List<TvShowSearchDTO> filterTvShows(double min, double max);

    void updateTvShowPicture(String name, String key, MultipartFile pic1) throws IOException;

    void saveTvShowDTO(TvShowAddDTO tvShowDTO) throws IOException;

    int tvShowsLastPageNumber();

    List<TvShowSearchDTO> tvShowsPaginationList(int pageNumber);

    long tvShowsSize();

    FilterResponseDTO filter(SearchFilterParameters parameters, int page);
}
