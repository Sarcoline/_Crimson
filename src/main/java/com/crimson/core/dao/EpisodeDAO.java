package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;

import java.util.List;


public interface EpisodeDAO extends BaseDAO<Episode, Long> {

    Episode getEpisodeByTitle(String title);

    Episode getBySeasonAndEpisodeNumber(int season, int number, long idTv);

    List<User> getUsers(Episode episode);

    long EpisodesSize();

}
