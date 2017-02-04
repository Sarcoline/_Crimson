package com.crimson.core.service;

import com.crimson.core.dao.EpisodeDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private EpisodeDAO episodeDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MapperFacade mapperFacade;

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
    public void addUser2Episode(UserDTO userDTO, Episode episode) {
        User user = userDAO.getUserById(userDTO.getId());
        //episodeDAO.addUser2Episode(user, episode);
        user.getUserEpisodeList().add(episode);
        userDAO.updateUser(user);
    }

    //TODO nie usuwa episode z usera
    @Override
    public void deleteUserFromEpisode(UserDTO userDTO, Episode episode) {
        User user = userDAO.getUserById(userDTO.getId());
        user.getUserEpisodeList().remove(episode);
        userDAO.saveUser(user);
    }

    @Override
    public boolean checkWatched(UserDTO userDTO, Episode episode) {
        return userDAO.getUserByName(userDTO.getName()).getUserEpisodeList().contains(episodeDAO.getEpisodeById(episode.getId()));
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
