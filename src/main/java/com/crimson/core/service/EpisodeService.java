package com.crimson.core.service;

import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;

import java.util.List;

public interface EpisodeService {
    void saveEpisode(Episode episode);

    void deleteEpisode(Episode episode);

    void updateEpisode(Episode episode);

    Episode getEpisodeById(Long idEpisode);

    List<Episode> getAllEpisodes();

    Episode getEpisodeByTitle(String title);

    void addUser2Episode(UserDTO userDTO, Episode episode);

    void deleteUserFromEpisode(UserDTO userDTO, Episode episode);

    boolean checkWatched(UserDTO userDTO, Episode episode);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(TvShow tvShow, Episode episode);
}
