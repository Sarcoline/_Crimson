package com.crimson.service;

import com.crimson.dao.TvShowDAO;
import com.crimson.dto.TvShowDTO;
import com.crimson.model.*;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Meow on 19.01.2017.
 */
@Service
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
        return  mapperFacade.map(tvShowDAO.getTvById(id), TvShowDTO.class);
    }

    @Override
    public TvShowDTO getTvBySlug(String slug) {
//        return tvShowDAO.getTvBySlug(slug);
        return mapperFacade.map(tvShowDAO.getTvBySlug(slug), TvShowDTO.class);
    }

    @Override
    public List<TvShow> getTvByGenre(String genre) {
        return tvShowDAO.getTvByGenre(genre);
    }

    @Override
    public HashMap<String, byte[]> getTvPictures(String slug) {
        return tvShowDAO.getTvBySlug(slug).getPictures();
    }

    @Override
    public void deleteTvShow(TvShow tvshow) {
        tvShowDAO.deleteTvShow(tvshow);
    }
    @Override
    public void updateTvShow(TvShow tvshow){
        tvShowDAO.updateTvShow(tvshow);
    }


    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow){
        tvShowDAO.addUser2TvShow(user,tvShow);
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow){
        tvShowDAO.deleteUserFromTvShow(user,tvShow);
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre){
        tvShowDAO.addGenre2TvShow(tvShow, genre);
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre){
        tvShowDAO.deleteGenreFromTvShow(tvShow,genre);
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode){
        tvShowDAO.addEpisode2TvShow(tvShow,episode);
    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode){
        tvShowDAO.deleteEpisodeFromTvShow(tvShow,episode);
    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating){
        tvShowDAO.addRating2TvShow(tvShow,rating);
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating){
        tvShowDAO.deleteRatingFromTvShow(tvShow,rating);
    }
}
