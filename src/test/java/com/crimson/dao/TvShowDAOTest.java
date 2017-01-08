package com.crimson.dao;

import com.crimson.WebConfig;
import com.crimson.model.TvShow;

import com.crimson.model.User;
import org.junit.After;
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

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class TvShowDAOTest {

    @Autowired
    TvShowDAO tvShowDAO;

    TvShow tv = new TvShow();
    TvShow tv2 = new TvShow();

    @Before
    public void setUp(){
        tv.setTitle("Show");
        tv.setGenre("Comedy");
        if(tvShowDAO.getAllTvShows().indexOf(tv) == -1)
            tvShowDAO.saveTvShow(tv);

        tv2.setTitle("New");
        tv2.setGenre("Drama");
        if(tvShowDAO.getAllTvShows().indexOf(tv2) == -1)
            tvShowDAO.saveTvShow(tv2);
    }

    @After
    public void TearDown(){
        if(tvShowDAO.getTvById(tv.getId()) != null)
            tvShowDAO.deleteTvShow(tv);
        if(tvShowDAO.getTvById(tv2.getId()) != null)
            tvShowDAO.deleteTvShow(tv2);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddTv() {
        assertEquals(tv.getTitle(), tvShowDAO.getTvById(tv.getId()).getTitle());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteTv(){
        List<User> users = tv.getUsers();
        tvShowDAO.deleteTvShow(tv);

        for(User user : users)
            assertEquals(-1,user.getTvShows().indexOf(tv));

        assertEquals(null,tvShowDAO.getTvById(tv.getId()));
        assertEquals(tv2,tvShowDAO.getTvById(tv2.getId()));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateTv(){
        tvShowDAO.getTvById(tv.getId()).setTitle("Update Test");
        tvShowDAO.getTvById(tv.getId()).setGenre("Update Test");
        tvShowDAO.updateTvShow(tvShowDAO.getTvById(tv.getId()));

        assertEquals("Update Test", tvShowDAO.getTvById(tv.getId()).getTitle());
        assertEquals("Update Test", tvShowDAO.getTvById(tv.getId()).getGenre());

        assertEquals(tv2,tvShowDAO.getTvById(tv2.getId()));
    }
}
