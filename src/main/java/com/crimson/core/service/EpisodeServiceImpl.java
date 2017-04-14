package com.crimson.core.service;

import com.crimson.core.dao.EpisodeDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.EpisodeDTO;
import com.crimson.core.dto.EpisodeFormDTO;
import com.crimson.core.dto.EpisodeFromJson;
import com.crimson.core.dto.TvShowDisplayDTO;
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
    private TvShowService tvShowService;

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
    public void addUser2Episode(String username, long id) {
        Episode episode = episodeDAO.getById(id);
        User user = userDAO.getUserByName(username);
        if (!userDAO.getEpisodes(user).contains(episode)) {
            user.getEpisodes().add(episode);
            userDAO.update(user);
        }
        if (!episodeDAO.getUsers(episode).contains(user)) {
            episode.getUsers().add(user);
            episodeDAO.update(episode);
        }

    }

    @Override
    public void deleteUserFromEpisode(String username, long id) {
        Episode episode = episodeDAO.getById(id);
        User user = userDAO.getUserByName(username);
        if (userDAO.getEpisodes(user).contains(episode)) {
            user.getEpisodes().remove(episode);
            userDAO.update(user);
        }
        if (episodeDAO.getUsers(episode).contains(user)) {
            episode.getUsers().remove(user);
            episodeDAO.update(episode);
        }
    }

    @Override
    public List<User> getUsers(Episode episode) {
        return episodeDAO.getUsers(episode);
    }

    @Override
    public boolean checkWatched(String userName, long id) {
        return userDAO.checkWatched(userName,id);
    }

    //TvShow2Episode

    @Override
    public void addTvShow2Episode(TvShow tvShow, Episode episode) {
        if (episode.getTvShow() != tvShow) {
            episode.setTvShow(tvShow);
            episodeDAO.update(episode);
        }
        if (!tvShowDAO.getEpisodes(tvShow).contains(episode)) {
            List<Episode> episodes = tvShowDAO.getEpisodes(tvShow);
            episodes.add(episode);
            tvShow.setEpisodes(episodes);
            tvShowDAO.update(tvShow);
        }
    }

    @Override
    public void deleteTvShowFromEpisode(TvShow tvShow, Episode episode) {
        if (episode.getTvShow() == tvShow) {
            episode.setTvShow(null);
            episodeDAO.update(episode);
        }
        if (tvShowDAO.getEpisodes(tvShow).contains(episode)) {
            List<Episode> episodes = tvShowDAO.getEpisodes(tvShow);
            episodes.remove(episode);
            tvShow.setEpisodes(episodes);
            tvShowDAO.update(tvShow);
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
                if (episode.getNumber() == episodeFromJson.getNumber()
                        && episode.getSeason() == episodeFromJson.getSeason()) exsist = true;
            }
            if (exsist) {
                epRet = episodeDAO.getBySeasonAndEpisodeNumber(
                        episodeFromJson.getSeason(), episodeFromJson.getNumber(), tvShowId);

                epRet.setReleaseDate(LocalDate.parse(episodeFromJson.getReleaseDate()));
                epRet.setTitle(episodeFromJson.getTitle());
                epRet.setSeason(episodeFromJson.getSeason());
                epRet.setNumber(episodeFromJson.getNumber());
                epRet.setEpisodeSummary(episodeFromJson.getEpisodeSummary());
                episodeDAO.update(epRet);
            } else {

                Episode ep = Episode.builder()
                        .episodeSummary(episodeFromJson.getEpisodeSummary())
                        .title(episodeFromJson.getTitle())
                        .number(episodeFromJson.getNumber())
                        .season(episodeFromJson.getSeason())
                        .releaseDate(LocalDate.parse(episodeFromJson.getReleaseDate()))
                        .idTvShow(episodeFromJson.getIdTvShow())
                        .build();
                episodeDAO.save(ep);
            }

        });
    }

    @Override
    public void addUserToSeason(String user, int season, String slug) {
        TvShowDisplayDTO tv = tvShowService.getDisplayBySlug(slug);
        tv.getEpisodes().forEach(episode -> {
            if (!checkWatched(user, episode.getId()) && episode.getSeason() == season) addUser2Episode(user, episode.getId());
        });
    }
}
