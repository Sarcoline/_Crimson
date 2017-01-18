package com.crimson.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.model.Episode;
import com.crimson.model.TvShow;
import com.crimson.model.User;
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
public class TestUserDAO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    private User user = new User();

    private TvShow tvShow = new TvShow();

    private Episode episode = new Episode();

    @Before
    public void setDB(){
        user.setName("Alex");
        userDAO.saveUser(user);

        tvShow.setTitle("Dr.House");
        tvShowDAO.saveTvShow(tvShow);

        episode.setTitle("First Day");
        episodeDAO.saveEpisode(episode);
    }

    @Test
    public void addUserTest(){
        user.setName("Alex");
        user.setEmail("Alex@wp.pl");
        user.setPassword("1234");
        user.setRole("ROLE_ADMIN");

        userDAO.saveUser(user);

        Assert.assertEquals(user.getName(), userDAO.getUserById(user.getId()).getName());
        Assert.assertEquals(user.getEmail(), userDAO.getUserById(user.getId()).getEmail());
        Assert.assertEquals(user.getPassword(), userDAO.getUserById(user.getId()).getPassword());
        Assert.assertEquals(1, userDAO.getAllUsers().size());
    }

    @Test
    public void updateUserTest(){
        user.setEmail("changeEmail");
        user.setPassword("changePassword");

        userDAO.updateUser(user);

        Assert.assertEquals(user.getEmail(), "changeEmail");
        Assert.assertEquals(user.getPassword(), "changePassword");
    }

    @Test
    public void deleteUserTest(){
        userDAO.deleteUser(user);

        Assert.assertEquals(null, userDAO.getUserById(user.getId()));
    }

    @Test
    public void getAllUsersTest(){
        int usersListSize = userDAO.getAllUsers().size();

        Assert.assertEquals(1, usersListSize);
    }

    @Test
    public void getUserByIdTest(){
        User getUserByIdTest = userDAO.getUserById(user.getId());

        Assert.assertEquals(getUserByIdTest.getName(), user.getName());
    }

    @Test
    public void getUserByNameTest(){
        User getUserByNameTest = userDAO.getUserByName("Alex");

        Assert.assertEquals(getUserByNameTest.getName(), user.getName());
    }

    //RELATIONSHIP TESTS

    //User2TvShow
    @Test
    public void addUser2TvShowTest(){
        int sizeOfUserTvShowsList = user.Users2TvShow.size();
        int sizeOfTvShow2UserList = tvShow.TvShows2User.size();

        userDAO.addUser2TvShows(user,tvShow);

        Assert.assertEquals(tvShow.TvShows2User.size(), sizeOfTvShow2UserList+1);
        Assert.assertEquals(user.Users2TvShow.size(), sizeOfUserTvShowsList+1);
    }

    @Test
    public void deleteUser2TvShowTest()
    {
        addUser2TvShowTest();

        int sizeOfUserTvShowsList = user.Users2TvShow.size();
        int sizeOfTvShow2UserList = tvShow.TvShows2User.size();

        userDAO.deleteUser2TvShow(user, tvShow);

        Assert.assertEquals(tvShow.TvShows2User.size(), sizeOfTvShow2UserList-1);
        Assert.assertEquals(user.Users2TvShow.size(), sizeOfUserTvShowsList-1);
    }


    //EpisodeWatched(User2Episode)

    @Test
    public void addUser2EpisodeTest(){
        int sizeOfUser2EpisodeList = user.Users2Episode.size();
        int sizeOfEpisode2UserList = episode.Episode2Users.size();

        userDAO.addUser2Episode(user, episode);

        Assert.assertEquals(sizeOfUser2EpisodeList+1, user.Users2Episode.size());
        Assert.assertEquals(sizeOfEpisode2UserList+1, episode.Episode2Users.size());
    }

    @Test
    public void deleteUser2EpisodeTest(){
        addUser2EpisodeTest();

        int sizeOfUser2EpisodeList = user.Users2Episode.size();
        int sizeOfEpisode2UserList = episode.Episode2Users.size();

        userDAO.delelteUser2Episode(user, episode);

        Assert.assertEquals(sizeOfUser2EpisodeList-1, user.Users2Episode.size());
        Assert.assertEquals(sizeOfEpisode2UserList-1, episode.Episode2Users.size());
    }

}
