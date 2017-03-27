package com.crimson.core.service;

import com.crimson.core.dao.EpisodeDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.EpisodeFormDTO;
import com.crimson.core.dto.EpisodeFromJson;
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
    private TvShowDAO tvShowDAO;

    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void saveEpisode(Episode episode) {
        episodeDAO.save(episode);
    }

    @Override
    public void deleteEpisode(EpisodeDTO episode) {
        episodeDAO.delete(episodeDAO.getById(episode.getId()));
    }

    @Override
    public void updateEpisode(EpisodeDTO episode) {
        episodeDAO.update(mapperFacade.map(episode, Episode.class));
    }

    @Override
    public EpisodeDTO getEpisodeById(Long idEpisode) {
        return mapperFacade.map(episodeDAO.getById(idEpisode), EpisodeDTO.class);
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodeDAO.getAll();
    }

    @Override
    public Episode getEpisodeByTitle(String title) {
        return episodeDAO.getEpisodeByTitle(title);
    }


    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Override
    public void addUser2Episode(UserDTO userDTO, EpisodeDTO episodeDTO) {
        User user = userDAO.getById(userDTO.getId());
        Episode episode = episodeDAO.getById(episodeDTO.getId());
        if (!userDAO.getEpisodes(user).contains(episode)) {
            userDAO.addEpisode2User(user, episode);
        }
        if (!episodeDAO.getUsers(episode).contains(user)) {
            episodeDAO.addUser2Episode(user, episode);
        }

    }

    @Override
    public void deleteUserFromEpisode(UserDTO userDTO, EpisodeDTO episodeDTO) {
        Episode episode = episodeDAO.getById(episodeDTO.getId());
        User user = userDAO.getById(userDTO.getId());
        if (userDAO.getEpisodes(user).contains(episode)) {
            userDAO.deleteEpisodeFromUser(user, episode);
        }
        if (episodeDAO.getUsers(episode).contains(user)) {
            episodeDAO.deleteUserFromEpisode(user, episode);
        }
    }

    @Override
    public List<User> getUsers(Episode episode) {
        return episodeDAO.getUsers(episode);
    }

    @Override
    public boolean checkWatched(UserDTO userDTO, EpisodeDTO episodeDTO) {
        Episode episode = episodeDAO.getById(episodeDTO.getId());
        return userDAO.getUserByName(userDTO.getName()).getEpisodes().contains(episodeDAO.getById(episode.getId()));
    }

    //TvShow2Episode

    @Override
    public void addTvShow2Episode(TvShow tvShow, Episode episode) {
        if (episode.getTvShow() != tvShow) {
            episodeDAO.addTvShow2Episode(tvShow, episode);
        }
        if (!tvShowDAO.getEpisodes(tvShow).contains(episode)) {
            tvShowDAO.addEpisode2TvShow(tvShow, episode);
        }
    }

    @Override
    public void deleteTvShowFromEpisode(TvShow tvShow, Episode episode) {
        if (episode.getTvShow() == tvShow) {
            episodeDAO.deleteTvShowFromEpisode(episode);
        }
        if (tvShowDAO.getEpisodes(tvShow).contains(episode)) {
            tvShowDAO.deleteEpisodeFromTvShow(tvShow, episode);
        }
    }


    @Override
    public EpisodeFormDTO getEisodeForm(Long id) {
        return mapperFacade.map(episodeDAO.getById(id), EpisodeFormDTO.class);
    }

    @Override
    public void updateEpisodeFromForm(EpisodeFormDTO episodeFormDTO) {
        Episode ep = episodeDAO.getById(episodeFormDTO.getId());
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

    @Override
    public void saveEpisodeJSON(List<EpisodeFromJson> episodesFromJson, long tvShowId) {

        episodesFromJson.forEach(episodeFromJson -> {


            Episode epRet;
            boolean exsist = false;
            for (Episode episode : tvShowDAO.getById(episodeFromJson.getIdTvShow()).getEpisodes()) {
                if (episode.getNumber() == episodeFromJson.getEpisode()
                        && episode.getSeason() == episodeFromJson.getSeason()) exsist = true;
            }
            if (exsist) {
                epRet = episodeDAO.getBySeasonAndEpisodeNumber(
                        episodeFromJson.getSeason(), episodeFromJson.getEpisode(), tvShowId);

                epRet.setReleaseDate(LocalDate.parse(episodeFromJson.getReleaseDate()));
                epRet.setTitle(episodeFromJson.getTitle());
                epRet.setSeason(episodeFromJson.getSeason());
                epRet.setNumber(episodeFromJson.getEpisode());
                epRet.setEpisodeSummary(episodeFromJson.getSummary());
                episodeDAO.update(epRet);
            } else {

                Episode ep = Episode.builder()
                        .episodeSummary(episodeFromJson.getSummary())
                        .title(episodeFromJson.getTitle())
                        .number(episodeFromJson.getEpisode())
                        .season(episodeFromJson.getSeason())
                        .releaseDate(LocalDate.parse(episodeFromJson.getReleaseDate()))
                        .idTvShow(episodeFromJson.getIdTvShow())
                        .build();
                episodeDAO.save(ep);
            }

        });
    }
}
