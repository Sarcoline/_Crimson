package com.crimson.core.service;

import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dto.ImageDTO;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.TvShowSearchDTO;
import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TvShowServiceImpl implements TvShowService {

    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public void saveTvShow(TvShow tvShow) {
        tvShowDAO.saveTvShow(tvShow);
    }

    @Override
    public List<TvShow> getAllTvShows() {
        return tvShowDAO.getAllTvShows();
    }

    @Override
    public TvShowDTO getTvById(Long id) {
        return mapperFacade.map(tvShowDAO.getTvById(id), TvShowDTO.class);
    }

    @Override
    public TvShowDTO getTvByIdWithEpisodes(Long id) {
        return mapperFacade.map(tvShowDAO.getTvByIdWithEpisodes(id), TvShowDTO.class);
    }

    @Override
    public TvShowDTO getTvBySlug(String slug) {
        return mapperFacade.map(tvShowDAO.getTvBySlug(slug), TvShowDTO.class);
    }

    @Override
    public List<TvShow> getTvByGenre(String genre) {
        return tvShowDAO.getTvByGenre(genre);
    }

    @Override
    public ImageDTO getTvPictures(String slug) {
        return mapperFacade.map(tvShowDAO.getTvBySlug(slug), ImageDTO.class);
    }

    @Override
    public void deleteTvShow(TvShow tvshow) {
        tvShowDAO.deleteTvShow(tvshow);
    }

    @Override
    public void updateTvShow(TvShowDTO tvshow) {
        Slugify slugify = new Slugify();
        TvShow tv = tvShowDAO.getTvById(tvshow.getId());
        tv.setCountry(tvshow.getCountry());
        tv.setNetwork(tvshow.getNetwork());
        tv.setDescription(tvshow.getDescription());
        tv.setReleaseYear(tvshow.getReleaseYear());
        tv.setGenre(tvshow.getGenre());
        tv.setTitle(tvshow.getTitle());
        tv.setSlug(slugify.slugify(tvshow.getTitle()));
        tvShowDAO.updateTvShow(tv);
    }


    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow) {
        tvShowDAO.addUser2TvShow(user, tvShow);
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow) {
        tvShowDAO.deleteUserFromTvShow(user, tvShow);
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre) {
        tvShowDAO.addGenre2TvShow(tvShow, genre);
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre) {
        tvShowDAO.deleteGenreFromTvShow(tvShow, genre);
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode) {
        tvShowDAO.addEpisode2TvShow(tvShow, episode);
    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode) {
        tvShowDAO.deleteEpisodeFromTvShow(tvShow, episode);
    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating) {
        tvShowDAO.addRating2TvShow(tvShow, rating);
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating) {
        tvShowDAO.deleteRatingFromTvShow(tvShow, rating);
    }

    //Extra Methods

    @Override
    public List<TvShowDTO> getAllTvShowByMaxRating() {
        List tvs = new ArrayList();
        tvShowDAO.getAllTvShowByMaxRating().forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowDTO.class)));
        return tvs;
    }

    @Override
    public List<TvShowSearchDTO> searchTvShow(String pattern) {
        List tvs = new ArrayList();
        tvShowDAO.searchTvShow(pattern).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class))
        );
        return tvs;
    }

    @Override
    public List<TvShowSearchDTO> filterTvShows(double min, double max) {
        List tvs = new ArrayList();
        tvShowDAO.filterTvShows(min,max).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class))
        );
        return tvs;
    }
}
