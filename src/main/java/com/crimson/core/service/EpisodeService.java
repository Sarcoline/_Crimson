package com.crimson.core.service;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.EpisodeFormDTO;
import com.crimson.core.dto.EpisodeFromJson;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;

public interface EpisodeService {

    void saveEpisode(Episode episode);

    void deleteEpisode(EpisodeDTO episode);

    void updateEpisode(EpisodeDTO episode);

    EpisodeDTO getEpisodeById(Long idEpisode);

    List<Episode> getAllEpisodes();

    Episode getEpisodeByTitle(String title);

    void addUser2Episode(UserDTO userDTO, EpisodeDTO episode);

    void deleteUserFromEpisode(UserDTO userDTO, EpisodeDTO episodeDTO);

    List<User> getUsers(Episode episode);

    boolean checkWatched(UserDTO userDTO, EpisodeDTO episodeDTO);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(TvShow tvShow, Episode episode);

    EpisodeFormDTO getEisodeForm(Long id);

    void updateEpisodeFromForm(EpisodeFormDTO episodeFormDTO);

    void addEpisodeFromForm(EpisodeFormDTO episodeFormDTO);

    void saveEpisodeJSON(List<EpisodeFromJson> episodeFromJson, long tvShowId);
}
