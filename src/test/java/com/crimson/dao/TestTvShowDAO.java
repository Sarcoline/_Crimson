package com.crimson.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.model.TvShow;
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

    private TvShow tvShow = new TvShow();

    @Before
    public void setDB(){
        tvShow.setTitle("GameOfAlex");
        tvShow.setCountry("Poland");
        tvShow.setGenre("Drama");
        tvShow.setReleaseYear(2017);
        tvShow.setDescription("Test");
        tvShow.setNetwork("Bojano INC");
        tvShow.setOverallRating(7.1);
        tvShow.setTrailerUrl("google.pl");

        tvShowDAO.saveTvShow(tvShow);
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
    public void getTvShowById(){
        TvShow getTvShowByIdTest = tvShowDAO.getTvById(tvShow.getId());

        Assert.assertEquals(getTvShowByIdTest.getTitle(), tvShow.getTitle());
    }
}
