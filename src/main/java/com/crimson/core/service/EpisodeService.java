package com.crimson.core.service;

import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.EpisodeFormDTO;
import com.crimson.core.dto.EpisodeFromJson;
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

    void addUser2Episode(String username, long id);

    void deleteUserFromEpisode(String username, long id);

    List<User> getUsers(Episode episode);

    boolean checkWatched(String username, long id);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(TvShow tvShow, Episode episode);

    EpisodeFormDTO getEisodeForm(Long id);

    void updateEpisodeFromForm(EpisodeFormDTO episodeFormDTO);

    void addEpisodeFromForm(EpisodeFormDTO episodeFormDTO);

    void saveEpisodeJSON(List<EpisodeFromJson> episodeFromJson, long tvShowId);

    void addUserToSeason(String username, int season, String slug);
}
