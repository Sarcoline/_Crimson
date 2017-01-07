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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class User2TvShowTest {

    @Autowired
    UserDAO userDAO;
    @Autowired
    TvShowDAO tvShowDAO;

    User user = new User();
    User user2 = new User();
    TvShow tv = new TvShow();
    TvShow tv2 = new TvShow();

    @Before
    public void setUp(){
        user.setName("Paula");
        user.setEmail("paula@gmail.com");
        user.setPassword("abc");
        userDAO.saveUser(user);

        user2.setName("Radek");
        user2.setEmail("radzio@gmail.com");
        user2.setPassword("123");
        userDAO.saveUser(user2);

        tv.setTitle("Show");
        tv.setGenre("Comedy");
        tvShowDAO.saveTvShow(tv);

        tv2.setTitle("New");
        tv2.setGenre("Drama");
        tvShowDAO.saveTvShow(tv2);
    }

    @After
    public void TearDown(){
        userDAO.deleteUser(user);
        userDAO.deleteUser(user2);

        tvShowDAO.deleteTvShow(tv);
        tvShowDAO.deleteTvShow(tv2);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddUser2TvShow() {
        userDAO.addUser2TvShow(user,tv);

        assertEquals("Show",user.getTvShows().get(user.getTvShows().indexOf(tv)).getTitle());
        assertEquals("Paula",tv.getUsers().get(tv.getUsers().indexOf(user)).getName());

        userDAO.deleteUser2TvShow(user,tv);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteTv2User() {
        userDAO.addUser2TvShow(user,tv);
        userDAO.addUser2TvShow(user,tv2);

        userDAO.deleteUser2TvShow(user,tv);

        assertEquals(-1,user.getTvShows().indexOf(tv));
        assertEquals(-1,tv.getUsers().indexOf(user));
    }
}
