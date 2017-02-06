package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
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

    private TvShow tvShow = TvShow.builder()
            .title("Dr.House")
            .network("Netflix")
            .country("US")
            .genre("Drama")
            .build();

    User user = User.builder()
            .name("Aleks")
            .email("Email@wp.pl")
            .password("123")
            .role("ROLE_USER")
            .build();

    private Genre genre = Genre.builder()
            .name("Drama")
            .build();

    private Episode episode = Episode.builder()
            .title("Episode 1")
            .build();

    private Rating rating = Rating.builder()
            .value(5)
            .build();

    @Before
    public void setDB() {
        tvShowDAO.saveTvShow(tvShow);
        genreDAO.addGenre(genre);
        episodeDAO.saveEpisode(episode);
        rating.setTvShowRating(tvShow);
    }

    @Test
    public void addTvShowTest() {
        tvShow.setTitle("AddTvShow");
        tvShow.setCountry("AddTvShow");
        tvShow.setGenre("AddTvShow");

        tvShowDAO.saveTvShow(tvShow);

        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getTitle());
        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getCountry());
        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getGenre());
    }

    @Test
    public void updateTvShowTest() {
        tvShow.setTitle("UpdateTest");
        tvShow.setCountry("UpdateTest");

        tvShowDAO.updateTvShow(tvShow);

        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getTvById(tvShow.getId()).getTitle());
        Assert.assertEquals(tvShow.getCountry(), tvShowDAO.getTvById(tvShow.getId()).getCountry());
    }

    @Test
    public void deleteTvShowTest() {
        tvShowDAO.deleteTvShow(tvShow);

        Assert.assertEquals(null, tvShowDAO.getTvById(tvShow.getId()));
    }

    @Test
    public void getAllTvShowsTest() {
        int sizeListTvShows = tvShowDAO.getAllTvShows().size();

        Assert.assertEquals(1, sizeListTvShows);
    }

    @Test
    public void getTvShowByIdTest() {
        TvShow getTvShowByIdTest = tvShowDAO.getTvById(tvShow.getId());

        Assert.assertEquals(getTvShowByIdTest.getTitle(), tvShow.getTitle());
    }

    //RELATIONSHIP TESTS

    //User2TvShow
    @Test
    public void addUser2TvShow() {
        int size = tvShow.getTvShowUserList().size();

        tvShowDAO.addUser2TvShow(user, tvShow);

        Assert.assertEquals(size + 1, tvShow.getTvShowUserList().size());
    }

    @Test
    public void deleteUserFromTvShow() {
        addUser2TvShow();

        int size = tvShow.getTvShowUserList().size();

        tvShowDAO.deleteUserFromTvShow(user, tvShow);

        Assert.assertEquals(size - 1, tvShow.getTvShowUserList().size());
    }

    //Genre2TvShow

    @Test
    public void addGenre2TvShow() {
        int size = tvShow.getTvShowGenreList().size();

        tvShowDAO.addGenre2TvShow(tvShow, genre);

        Assert.assertEquals(size + 1, tvShow.getTvShowGenreList().size());
    }

    @Test
    public void deleteGenreFromTvShow() {
        addGenre2TvShow();

        int size = tvShow.getTvShowGenreList().size();

        tvShowDAO.deleteGenreFromTvShow(tvShow, genre);

        Assert.assertEquals(size - 1, tvShow.getTvShowGenreList().size());
    }

    //TvShow2Episode

    @Test
    public void addEpisode2TvShow() {
        int size = tvShow.getEpisodes().size();

        tvShowDAO.addEpisode2TvShow(tvShow, episode);

        Assert.assertEquals(size + 1, tvShow.getEpisodes().size());
    }

    @Test
    public void deleteEpisodeFromTvShow() {
        addEpisode2TvShow();

        int size = tvShow.getEpisodes().size();

        tvShowDAO.deleteEpisodeFromTvShow(tvShow, episode);

        Assert.assertEquals(size - 1, tvShow.getEpisodes().size());
    }

    @Test
    public void addRating2TvShow() {
        int size = tvShow.getTvShowRating().size();

        tvShowDAO.addRating2TvShow(tvShow, rating);

        Assert.assertEquals(size + 1, tvShow.getTvShowRating().size());
    }

    @Test
    public void deleteRatingFromTvShow() {
        addRating2TvShow();

        int size = tvShow.getTvShowRating().size();

        tvShowDAO.deleteRatingFromTvShow(tvShow, rating);

        Assert.assertEquals(size - 1, tvShow.getTvShowRating().size());
    }
}