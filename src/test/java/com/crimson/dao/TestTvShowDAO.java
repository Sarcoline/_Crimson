package com.crimson.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@WebAppConfiguration
@Transactional
@Rollback(value = true)
public class TestTvShowDAO {

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private RatingDAO ratingDAO;

    private TvShow tvShow = new TvShow();

    private User user = new User();

    private Genre genre = new Genre();

    private Episode episode = new Episode();

    private Rating rating = new Rating();

    @Before
    public void setDB(){
        tvShow.setTitle("Gameów Of Alex");
        tvShow.setCountry("Poland");
        tvShow.setGenre("Drama");
        tvShow.setReleaseYear(2017);
        tvShow.setDescription("Test");
        tvShow.setNetwork("Bojano INC");
        tvShow.setOverallRating(7.1);
        tvShow.setTrailerUrl("google.pl");

        tvShowDAO.saveTvShow(tvShow);

        user.setName("Alex");
        userDAO.saveUser(user);

        genre.setName("Drama");
        genreDAO.addGenre(genre);

        episode.setTitle("EP1");
        episode.setIdTvShow(tvShow.getId());
        episodeDAO.saveEpisode(episode);

        rating.setValue(6);
        rating.setUserRating(user);
        rating.setTvShowRating(tvShow);
    }

    @Test
    public void addTvShowTest(){
        tvShow.setTitle("AddTvShow");
        tvShow.setCountry("AddTvShow");
        tvShow.setGenre("AddTvShow");

        tvShowDAO.saveTvShow(tvShow);

        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getTitle());
        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getCountry());
        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getGenre());
    }

    @Test
    public void updateTvShowTest(){
        tvShow.setTitle("UpdateTest");
        tvShow.setCountry("UpdateTest");

        tvShowDAO.updateTvShow(tvShow);

        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getTitle());
        Assert.assertEquals(tvShow.getCountry(), tvShowDAO.getTvById(tvShow.getId()).getCountry());
    }

    @Test
    public void deleteTvShowTest(){
        tvShowDAO.deleteTvShow(tvShow);

        Assert.assertEquals(null, tvShowDAO.getTvById(tvShow.getId()));
    }

    @Test
    public void getAllTvShowsTest(){
        int sizeListTvShows = tvShowDAO.getAllTvShows().size();

        Assert.assertEquals(1, sizeListTvShows);
    }

    @Test
    public void getTvShowByIdTest(){
        TvShow getTvShowByIdTest = tvShowDAO.getTvById(tvShow.getId());

        Assert.assertEquals(getTvShowByIdTest.getTitle(), tvShow.getTitle());
    }

    //RELATIONSHIP TESTS

    //User2TvShow
    @Test
    public void addUser2TvShow(){
        int size = tvShow.getTvShowUserList().size();

        tvShowDAO.addUser2TvShow(user, tvShow);

        Assert.assertEquals(size+1, tvShow.getTvShowUserList().size());
    }

    @Test
    public void deleteUserFromTvShow(){
        addUser2TvShow();

        int size = tvShow.getTvShowUserList().size();

        tvShowDAO.deleteUserFromTvShow(user, tvShow);

        Assert.assertEquals(size-1, tvShow.getTvShowUserList().size());
    }

    //Genre2TvShow

    @Test
    public void addGenre2TvShow(){
        int size = tvShow.getTvShowGenreList().size();

        tvShowDAO.addGenre2TvShow(tvShow,genre);

        Assert.assertEquals(size+1, tvShow.getTvShowGenreList().size());
    }

    @Test
    public void deleteGenreFromTvShow(){
        addGenre2TvShow();

        int size = tvShow.getTvShowGenreList().size();

        tvShowDAO.deleteGenreFromTvShow(tvShow,genre);

        Assert.assertEquals(size-1, tvShow.getTvShowGenreList().size());
    }

    //TvShow2Episode

    @Test
    public void addEpisode2TvShow(){
        int size = tvShow.getEpisodes().size();

        tvShowDAO.addEpisode2TvShow(tvShow, episode);

        Assert.assertEquals(size+1, tvShow.getEpisodes().size());
    }

    @Test
    public void deleteEpisodeFromTvShow(){
        addEpisode2TvShow();

        int size = tvShow.getEpisodes().size();

        tvShowDAO.deleteEpisodeFromTvShow(tvShow, episode);

        Assert.assertEquals(size - 1, tvShow.getEpisodes().size());
    }

    @Test
    public void addRating2TvShow(){
        int size = tvShow.getTvShowRating().size();

        tvShowDAO.addRating2TvShow(tvShow, rating);

        Assert.assertEquals(size+1, tvShow.getTvShowRating().size());
    }

    @Test
    public void deleteRatingFromTvShow(){
        addRating2TvShow();

        int size = tvShow.getTvShowRating().size();

        tvShowDAO.deleteRatingFromTvShow(tvShow, rating);

        Assert.assertEquals(size-1, tvShow.getTvShowRating().size());
    }
}
