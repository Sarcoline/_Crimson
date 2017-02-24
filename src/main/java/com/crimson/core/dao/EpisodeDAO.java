package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;


public interface EpisodeDAO extends BaseDAO<Episode, Long> {

    Episode getEpisodeByTitle(String title);

    void addUser2Episode(User user, Episode episode);

    void deleteUserFromEpisode(User user, Episode episode);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(TvShow tvShow, Episode episode);
}
