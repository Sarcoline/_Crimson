package com.crimson.dao;

import com.crimson.WebConfig;
import com.crimson.model.TvShow;
import com.crimson.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class TvShow_UserTest {

    @Autowired
    UserDAO userDAO;
    @Autowired
    TvShowDAO tvShowDAO;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddTv2User() {
        User user = new User();
        user.setName("Paula");
        user.setEmail("paula@gmail.com");
        user.setPassword("abc");
        userDAO.saveUser(user);

        TvShow tv = new TvShow();
        tv.setTitle("Show");
        tv.setGenre("Comedy");
        tvShowDAO.saveTvShow(tv);

        user.getTvShows().add(tv);
        tv.getUsers().add(user);

        List<TvShow> tvshows = user.getTvShows();
        assertEquals("Show",tvshows.get(user.getTvShows().size()-1).getTitle());

        List<User> users = tv.getUsers();
        assertEquals("Paula",users.get(tv.getUsers().size()-1).getName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteTv2User() {
        testAddTv2User();

        List<TvShow> tvshows = tvShowDAO.getAllTvShows();

        TvShow tv = new TvShow();
        tv.setTitle("New");
        tv.setGenre("Drama");
        tvShowDAO.saveTvShow(tv);

        List<User> users = userDAO.getAllUsers();
        User user = users.get(users.size()-1);

        List<TvShow> tvshows2 = tvShowDAO.getAllTvShows();

        user.getTvShows().add(tv);
        tv.getUsers().add(user);

        int size = user.getTvShows().size();
        int s = tv.getUsers().size();

        user.getTvShows().remove(tv);
        tv.getUsers().remove(user);

        assertEquals(-1,user.getTvShows().indexOf(tv));
        assertEquals(-1,tv.getUsers().indexOf(user));

        assertEquals(size-1,user.getTvShows().size());
        assertEquals(tvshows.size()+1,tvshows2.size());
        assertEquals(s-1,tv.getUsers().size());

    }
}
