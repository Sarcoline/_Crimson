package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;


public interface EpisodeDAO {
    void save(Episode episode);

    void delete(Episode episode);

    void update(Episode episode);

    Episode getEpisodeById(Long idEpisode);

    List<Episode> getAllEpisodes();

    Episode getEpisodeByTitle(String title);

    void addUser2Episode(User user, Episode episode);

    void deleteUserFromEpisode(User user, Episode episode);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(TvShow tvShow, Episode episode);
}
