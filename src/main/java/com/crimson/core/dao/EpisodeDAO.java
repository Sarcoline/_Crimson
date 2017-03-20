package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;


public interface EpisodeDAO extends BaseDAO<Episode, Long> {

    Episode getEpisodeByTitle(String title);

    Episode getBySeasonAndEpisodeNumber(int season, int number, long idTv);

    void addUser2Episode(User user, Episode episode);

    void deleteUserFromEpisode(User user, Episode episode);

    void addTvShow2Episode(TvShow tvShow, Episode episode);

    void deleteTvShowFromEpisode(Episode episode);

    List<User> getUsers(Episode episode);
}
