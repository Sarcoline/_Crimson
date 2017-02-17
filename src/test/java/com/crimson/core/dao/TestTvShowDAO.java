package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.factory.TvShowFactory;
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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback()
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

    private TvShowFactory tvShowFactory = new TvShowFactory();


    private User user = User.builder()
            .name("Aleks")
            .email("Email@wp.pl")
            .password("123")
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

    private TvShow tvShow = tvShowFactory.getTvShow("test1");
    private TvShow tvShow2 = tvShowFactory.getTvShow("test2");
    private TvShow tvShow3 = tvShowFactory.getTvShow("test3");
    private TvShow tvShow4 = tvShowFactory.getTvShow("test4");
    private TvShow tvShow5 = tvShowFactory.getTvShow("test5");
    private TvShow tvShow6 = tvShowFactory.getTvShow("test6");
    private TvShow tvShow7 = tvShowFactory.getTvShow("test7");
    private TvShow tvShow8 = tvShowFactory.getTvShow("test8");
    private TvShow tvShow9 = tvShowFactory.getTvShow("test9");
    private TvShow tvShow10 = tvShowFactory.getTvShow("test10");


    @Before
    public void setDB() {
        tvShowDAO.saveTvShow(tvShow);
        tvShowDAO.saveTvShow(tvShow2);
        tvShowDAO.saveTvShow(tvShow3);
        tvShowDAO.saveTvShow(tvShow4);
        tvShowDAO.saveTvShow(tvShow5);
        tvShowDAO.saveTvShow(tvShow6);
        tvShowDAO.saveTvShow(tvShow7);
        tvShowDAO.saveTvShow(tvShow8);
        tvShowDAO.saveTvShow(tvShow9);
        tvShowDAO.saveTvShow(tvShow10);

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

        Assert.assertEquals(10, sizeListTvShows);
    }

    @Test
    public void getTvShowByIdTest() {
        TvShow getTvShowByIdTest = tvShowDAO.getTvById(tvShow.getId());

        Assert.assertEquals(getTvShowByIdTest.getTitle(), tvShow.getTitle());
    }

    //Extra Methods

    @Test
    public void getAllTvShowByMaxRating(){
        List<TvShow> sortedList = tvShowDAO.getAllTvShowByMaxRating();

        Assert.assertEquals(sortedList.get(0).getOverallRating(), tvShow2.getOverallRating());
        Assert.assertEquals(sortedList.get(1).getOverallRating(), tvShow3.getOverallRating());
        Assert.assertEquals(sortedList.get(2).getOverallRating(), tvShow.getOverallRating());
    }

    @Test
    public void getTvShowsSortedByNumberOnList(){
        List<TvShow> gettedList = tvShowDAO.getTvShowsSortedByNumberOnList(10,1);


        Assert.assertEquals(gettedList.get(9), tvShow10);
        Assert.assertEquals(gettedList.get(8), tvShow9);
        Assert.assertEquals(gettedList.get(7), tvShow8);
        Assert.assertEquals(gettedList.get(6), tvShow7);
        Assert.assertEquals(gettedList.get(5), tvShow6);
        Assert.assertEquals(gettedList.get(4), tvShow5);
        Assert.assertEquals(gettedList.get(3), tvShow4);
        Assert.assertEquals(gettedList.get(2), tvShow3);
        Assert.assertEquals(gettedList.get(1), tvShow2);
        Assert.assertEquals(gettedList.get(0), tvShow);

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