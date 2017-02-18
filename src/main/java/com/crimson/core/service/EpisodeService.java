package com.crimson.core.service;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;

import java.util.List;

public interface EpisodeService {
    void saveEpisode(Episode episode);

    void deleteEpisode(Episode episode);

    void updateEpisode(Episode episode);

    EpisodeDTO getEpisodeById(Long idEpisode);

    List<Episode> getAllEpisodes();

    Episode getEpisodeByTitle(String title);

    void addUser2Episode(UserDTO userDTO, EpisodeDTO episode);

    void deleteUserFromEpisode(UserDTO userDTO, EpisodeDTO episodeDTO);

    boolean checkWatched(UserDTO userDTO, EpisodeDTO episodeDTO);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(TvShow tvShow, Episode episode);
}
