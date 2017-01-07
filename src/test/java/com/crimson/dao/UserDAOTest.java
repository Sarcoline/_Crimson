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
public class UserDAOTest {


    @Autowired
    UserDAO userDAO;

    @Autowired
    TvShowDAO tvShowDAO;

    User user = new User();
    User user2 = new User();

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
    }

    @After
    public void TearDown(){
        userDAO.deleteUser(user);
        userDAO.deleteUser(user2);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddUser() {
        assertEquals(user.getName(), userDAO.getUserById(user.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteUser(){
        List<TvShow> tvShows = user.getTvShows();
        userDAO.deleteUser(user);

        for(TvShow tvshow : tvShows)
            assertEquals(-1,tvshow.getUsers().indexOf(user));

        assertEquals(null, userDAO.getUserById(user.getId()));
        assertEquals(user2,userDAO.getUserById(user2.getId()));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateUser(){
        userDAO.getUserById(user.getId()).setName("Update Test");
        userDAO.getUserById(user.getId()).setEmail("Update Test");
        userDAO.updateUser(userDAO.getUserById(user.getId()));

        assertEquals("Update Test", userDAO.getUserById(user.getId()).getName());
        assertEquals("Update Test", userDAO.getUserById(user.getId()).getEmail());

        assertEquals(user2,userDAO.getUserById(user2.getId()));
    }
}
