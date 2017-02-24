package com.crimson.core.service;

import com.crimson.core.dao.EpisodeDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.EpisodeFormDTO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        episodeDAO.save(episode);
    }

    @Override
    public void deleteEpisode(EpisodeDTO episode) {
        episodeDAO.delete(episodeDAO.getEpisodeById(episode.getId()));
    }

    @Override
    public void updateEpisode(EpisodeDTO episode) {
        episodeDAO.update(mapperFacade.map(episode, Episode.class));
    }

    @Override
    public EpisodeDTO getEpisodeById(Long idEpisode) {
        return mapperFacade.map(episodeDAO.getEpisodeById(idEpisode), EpisodeDTO.class);
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
    public void addUser2Episode(UserDTO userDTO, EpisodeDTO episodeDTO) {
        User user = userDAO.getUserById(userDTO.getId());
        Episode episode = episodeDAO.getEpisodeById(episodeDTO.getId());
        userDAO.addEpisode2User(user, episode);

    }

    //TODO naprawic usuwanie episode z usera?
    @Override
    public void deleteUserFromEpisode(UserDTO userDTO, EpisodeDTO episodeDTO) {
        Episode episode = episodeDAO.getEpisodeById(episodeDTO.getId());
        User user = userDAO.getUserById(userDTO.getId());
        userDAO.deleteEpisodeFromUser(user, episode);
    }

    @Override
    public boolean checkWatched(UserDTO userDTO, EpisodeDTO episodeDTO) {
        Episode episode = episodeDAO.getEpisodeById(episodeDTO.getId());
        return userDAO.getUserByName(userDTO.getName()).getEpisodes().contains(episodeDAO.getEpisodeById(episode.getId()));
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


    @Override
    public EpisodeFormDTO getEisodeForm(Long id) {
        return mapperFacade.map(episodeDAO.getEpisodeById(id), EpisodeFormDTO.class);
    }

    @Override
    public void updateEpisodeFromForm(EpisodeFormDTO episodeFormDTO) {
       Episode ep  = episodeDAO.getEpisodeById(episodeFormDTO.getId());
        ep.setReleaseDate(LocalDate.parse(episodeFormDTO.getReleaseDate()));
        ep.setTitle(episodeFormDTO.getTitle());
        ep.setSeason(episodeFormDTO.getSeason());
        ep.setNumber(episodeFormDTO.getNumber());
        ep.setEpisodeSummary(episodeFormDTO.getEpisodeSummary());
        episodeDAO.update(ep);
    }

    @Override
    public void addEpisodeFromForm(EpisodeFormDTO episodeFormDTO) {
        Episode ep = Episode.builder()
                .episodeSummary(episodeFormDTO.getEpisodeSummary())
                .title(episodeFormDTO.getTitle())
                .number(episodeFormDTO.getNumber())
                .season(episodeFormDTO.getSeason())
                .releaseDate(LocalDate.parse(episodeFormDTO.getReleaseDate()))
                .idTvShow(episodeFormDTO.getIdTvShow())
                .build();
        episodeDAO.save(ep);
    }
}
