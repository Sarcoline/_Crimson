package com.crimson.core.service;

import com.crimson.core.dto.ImageDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.TvShowSearchDTO;
import com.crimson.core.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TvShowService {
    void saveTvShow(TvShow tvShow);

    List<TvShow> getAllTvShows();

    TvShowDTO getTvById(Long id);

    TvShowDTO getTvByIdWithEpisodes(Long id);

    TvShowDTO getTvBySlug(String slug);

    List<TvShow> getTvByGenre(String genre);

    List<TvShow> getTvByCountry(String country);

    void deleteTvShow(TvShowDTO tvshow);

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

    //Extra Methods
    List<TvShowDTO> getAllTvShowByMaxRating();

    List<TvShowSearchDTO> searchTvShow(String pattern);

    List<TvShowSearchDTO> filterTvShows(double min, double max);

    void updateTvShowPicture(String name, String key, MultipartFile pic1) throws IOException;

    void saveTvShowDTO(TvShowDTO tvShowDTO) throws IOException;
}
