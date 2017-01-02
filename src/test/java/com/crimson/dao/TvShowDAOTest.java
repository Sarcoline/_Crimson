package com.crimson.dao;

import com.crimson.WebConfig;
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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class TvShowDAOTest {

    @Autowired
    TvShowDAO tvShowDAO;

    private int tvshowsSize;

    @Before
    public void size() {
        tvshowsSize = tvShowDAO.getAllTvShows().size();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddTv() {
        TvShow tvshow = new TvShow();
        tvshow.setTitle("Title");
        tvshow.setGenre("Genre");
        tvshow.setCountry("USA");
        tvShowDAO.saveTvShow(tvshow);
        Assert.assertEquals(tvshow.getTitle(), tvShowDAO.getTvById(tvshow.getId()).getTitle());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteTv(){

        List<TvShow> tvshows = tvShowDAO.getAllTvShows();
        int tvSizeBefore = tvshows.size()-1;
        Long idTv = tvshows.get(tvShowDAO.getAllTvShows().size()-1).getId();
        tvShowDAO.deleteTvShow(tvShowDAO.getTvById(idTv));

        Assert.assertEquals(null, tvShowDAO.getTvById(idTv));
        Assert.assertEquals(tvSizeBefore, tvShowDAO.getAllTvShows().size());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateTv(){

        List<TvShow> tvshows = tvShowDAO.getAllTvShows();
        Long idTv;

        if(tvshows.size() == 0){
            testAddTv();
            tvshows = tvShowDAO.getAllTvShows();
            idTv = tvshows.get(tvShowDAO.getAllTvShows().size()-1).getId();
        }
        else{
            idTv = tvshows.get(tvShowDAO.getAllTvShows().size()-1).getId();
        }

        tvShowDAO.getTvById(idTv).setTitle("New");
        tvShowDAO.getTvById(idTv).setGenre("G");
        tvShowDAO.updateTvShow(tvShowDAO.getTvById(idTv));

        Assert.assertEquals("New", tvShowDAO.getTvById(idTv).getTitle());
        Assert.assertEquals("G", tvShowDAO.getTvById(idTv).getGenre());

    }
}
