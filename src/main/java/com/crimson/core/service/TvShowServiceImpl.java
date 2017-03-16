package com.crimson.core.service;

import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dto.*;
import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Transactional
public class TvShowServiceImpl implements TvShowService {

    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void saveTvShow(TvShow tvShow) {
        tvShowDAO.save(tvShow);
    }

    @Override
    public List<TvShow> getAllTvShows() {
        return tvShowDAO.getAll();
    }

    @Override
    public TvShowDTO getTvById(Long id) {
        return mapperFacade.map(tvShowDAO.getById(id), TvShowDTO.class);
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
    public List<TvShow> getTvByCountry(String country) {
        return tvShowDAO.getTvByCountry(country);
    }

    @Override
    public List<TvShow> getTvByYear(int releaseYear) {
        return tvShowDAO.getTvByYear(releaseYear);
    }

    @Override
    public List<TvShow> getTvByNetwork(String network) {
        return tvShowDAO.getTvByNetwork(network);
    }


    @Override
    public ImageDTO getTvPictures(String slug) {
        return mapperFacade.map(tvShowDAO.getTvBySlug(slug), ImageDTO.class);
    }

    @Override
    public void deleteTvShow(TvShowDTO tvshow) {
        TvShow tv = tvShowDAO.getById(tvshow.getId());
        tvShowDAO.delete(tv);
    }

    @Override
    public void updateTvShow(TvShowDTO tvshow) {
        Slugify slugify = new Slugify();
        TvShow tv = tvShowDAO.getById(tvshow.getId());
        tv.setCountry(tvshow.getCountry());
        tv.setNetwork(tvshow.getNetwork());
        tv.setDescription(tvshow.getDescription());
        tv.setReleaseYear(tvshow.getReleaseYear());
        tv.setGenre(tvshow.getGenre());
        tv.setTitle(tvshow.getTitle());
        tv.setSlug(slugify.slugify(tvshow.getTitle()));
        tvShowDAO.update(tv);
    }

    @Override
    public long tvShowsSize() {
        return tvShowDAO.tvShowsSize();
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
        List<TvShowDTO> tvs = new ArrayList<>();
        List<TvShow> unsortedList = tvShowDAO.getAll();
        unsortedList.sort(Comparator.comparing(TvShow::getOverallRating).reversed());
        unsortedList.forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowDTO.class)));
        return tvs;
    }

    @Override
    public List<TvShowSearchDTO> searchTvShow(String pattern) {
        List<TvShowSearchDTO> tvs = new ArrayList<>();
        tvShowDAO.searchTvShow(pattern).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class))
        );
        return tvs;
    }

    @Override
    public List<TvShowSearchDTO> filterTvShows(double min, double max) {
        List<TvShowSearchDTO> tvs = new ArrayList<>();
        tvShowDAO.filterTvShows(min, max).forEach(
                tv -> tvs.add(mapperFacade.map(tv, TvShowSearchDTO.class))
        );
        return tvs;
    }

    @Override
    public void updateTvShowPicture(String name, String key, MultipartFile pic1) throws IOException {
        TvShow tv = tvShowDAO.getTvBySlug(name);
        tv.getPictures().put(key, pic1.getBytes());
        tvShowDAO.update(tv);
    }

    @Override
    public void saveTvShowDTO(TvShowAddDTO tvShowAddDTO) throws IOException {
        TvShow tv = mapperFacade.map(tvShowAddDTO, TvShow.class);
        tv.getPictures().put("1", tvShowAddDTO.getPic1().getBytes());
        tv.getPictures().put("2", tvShowAddDTO.getPic2().getBytes());
        tv.getPictures().put("3", tvShowAddDTO.getPic3().getBytes());
        tv.getPictures().put("back", tvShowAddDTO.getBack().getBytes());
        tv.getPictures().put("poster", tvShowAddDTO.getPoster().getBytes());
        tvShowDAO.save(tv);
    }

    @Override
    public int tvShowsLastPageNumber() {
        return tvShowDAO.tvShowsLastPageNumber();
    }

    @Override
    public List<TvShowSearchDTO> tvShowsPaginationList(int pageNumber) {
        List<TvShowSearchDTO> tvShows = new ArrayList<>();
        tvShowDAO.tvShowsPaginationList(pageNumber).forEach(
                tvShow -> tvShows.add(mapperFacade.map(tvShow, TvShowSearchDTO.class))
        );
        return tvShows;
    }

    @Override
    public FilterResponseDTO filter(SearchFilterParameters parameters, int page) {
        return mapperFacade.map(tvShowDAO.filter(parameters,page), FilterResponseDTO.class);
    }
}
