package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.model.Episode;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
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
public class TestUserDAO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private RatingDAO ratingDAO;

    private User user = new User.Builder()
            .name("Aleks")
            .email("Email@wp.pl")
            .password("123")
            .role("ROLE_USER")
            .build();

    private TvShow tvShow = new TvShow.Builder()
            .title("Dr.House")
            .network("Netflix")
            .country("US")
            .genre("Drama")
            .build();

    private Episode episode = new Episode.Builder()
            .title("Episode 1")
            .build();

    private Rating rating;

    @Before
    public void setDB() {

        userDAO.saveUser(user);
        tvShowDAO.saveTvShow(tvShow);
        episodeDAO.saveEpisode(episode);

    }

    @Test
    public void addUserTest() {
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
    public void updateUserTest() {
        user.setEmail("changeEmail");
        user.setPassword("changePassword");

        userDAO.updateUser(user);

        Assert.assertEquals(user.getEmail(), "changeEmail");
        Assert.assertEquals(user.getPassword(), "changePassword");
    }

    @Test
    public void deleteUserTest() {
        userDAO.deleteUser(user);

        Assert.assertEquals(null, userDAO.getUserById(user.getId()));
    }

    @Test
    public void getAllUsersTest() {
        int usersListSize = userDAO.getAllUsers().size();

        Assert.assertEquals(1, usersListSize);
    }

    @Test
    public void getUserByIdTest() {
        User getUserByIdTest = userDAO.getUserById(user.getId());

        Assert.assertEquals(getUserByIdTest.getName(), user.getName());
    }

    @Test
    public void getUserByNameTest() {
        User getUserByNameTest = userDAO.getUserByName("Aleks");

        Assert.assertEquals(getUserByNameTest.getName(), user.getName());
    }

    //RELATIONSHIP TESTS

    //User2TvShow
    @Test
    public void addTvShow2User() {
        int size = user.getUserTvShowList().size();

        userDAO.addTvShow2User(user, tvShow);
        Assert.assertEquals(size + 1, user.getUserTvShowList().size());
    }

    @Test
    public void deleteTvShowFromUser() {
        addTvShow2User();

        int size = user.getUserTvShowList().size();

        userDAO.deleteTvShowFromUser(user, tvShow);

        Assert.assertEquals(size - 1, user.getUserTvShowList().size());
    }

    //User2Episode

    @Test
    public void addEpisode2User() {
        int size = user.getUserEpisodeList().size();

        userDAO.addEpisode2User(user, episode);

        Assert.assertEquals(size + 1, user.getUserEpisodeList().size());
    }

    @Test
    public void deleteEpisodeFromUser() {
        addEpisode2User();

        int size = user.getUserEpisodeList().size();

        userDAO.deleteEpisodeFromUser(user, episode);

        Assert.assertEquals(size - 1, user.getUserEpisodeList().size());
    }

    //Rating
    @Test
    public void addRating2User() {
        int size = user.getUserRatings().size();

        userDAO.addRating2User(user, rating);

        Assert.assertEquals(size + 1, user.getUserRatings().size());
    }

    @Test
    public void deleteRatingFromUser() {
        addRating2User();

        int size = user.getUserRatings().size();

        userDAO.deleteRatingFromUser(user, rating);

        Assert.assertEquals(size - 1, user.getUserRatings().size());
    }


}