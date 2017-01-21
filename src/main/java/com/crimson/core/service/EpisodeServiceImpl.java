package com.crimson.core.service;

import com.crimson.core.dao.EpisodeDAO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private EpisodeDAO episodeDAO;

    @Override
    public void saveEpisode(Episode episode) {
        episodeDAO.saveEpisode(episode);
    }

    @Override
    public void deleteEpisode(Episode episode) {
        episodeDAO.deleteEpisode(episode);
    }

    @Override
    public void updateEpisode(Episode episode) {
        episodeDAO.updateEpisode(episode);
    }

    @Override
    public Episode getEpisodeById(Long idEpisode) {
        return episodeDAO.getEpisodeById(idEpisode);
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodeDAO.getAllEpisodes();
    }

    @Override
    public Episode getEpisodeByTitle(String title) {
        return episodeDAO.getEpisodeByTitle(title);
    }


    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Override
    public void addUser2Episode(User user, Episode episode) {
        episodeDAO.addUser2Episode(user, episode);
    }

    @Override
    public void deleteUserFromEpisode(User user, Episode episode) {
        episodeDAO.deleteUserFromEpisode(user, episode);
    }

    //TvShow2Episode

    @Override
    public void addTvShow2Episode(TvShow tvShow, Episode episode) {
        episodeDAO.addTvShow2Episode(tvShow, episode);
    }

    @Override
    public void deleteTvShowFromEpisode(TvShow tvShow, Episode episode) {
        episodeDAO.deleteTvShowFromEpisode(tvShow, episode);
    }
}
