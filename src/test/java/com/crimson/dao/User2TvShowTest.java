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
        if(userDAO.getAllUsers().indexOf(user) == -1)
            userDAO.saveUser(user);

        user2.setName("Radek");
        user2.setEmail("radzio@gmail.com");
        user2.setPassword("123");
        if(userDAO.getAllUsers().indexOf(user2) == -1)
            userDAO.saveUser(user2);

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
        if(userDAO.getUserById(user.getId()) != null)
        userDAO.deleteUser(user);
        if(userDAO.getUserById(user2.getId()) != null)
        userDAO.deleteUser(user2);

        if(tvShowDAO.getTvById(tv.getId()) != null)
        tvShowDAO.deleteTvShow(tv);
        if(tvShowDAO.getTvById(tv2.getId()) != null)
        tvShowDAO.deleteTvShow(tv2);
    }

    @Test
    @Transactional
    @Rollback
    public void testAddUser2TvShow() {
        userDAO.addUser2TvShow(user,tv);

        assertEquals("Show",user.getTvShows().get(user.getTvShows().indexOf(tv)).getTitle());
        assertEquals("Paula",tv.getUsers().get(tv.getUsers().indexOf(user)).getName());

        userDAO.deleteUser2TvShow(user,tv);
        assertEquals(-1,user.getTvShows().indexOf(tv));
        assertEquals(-1,tv.getUsers().indexOf(user));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteUser2TvShow() {
        userDAO.addUser2TvShow(user,tv);
        userDAO.addUser2TvShow(user,tv2);

        userDAO.deleteUser2TvShow(user,tv);
        assertEquals(-1,user.getTvShows().indexOf(tv));
        assertEquals(-1,tv.getUsers().indexOf(user));
    }

}
