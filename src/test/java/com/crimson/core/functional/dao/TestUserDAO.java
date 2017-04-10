package com.crimson.core.functional.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.*;
import com.crimson.core.factory.*;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback
public class TestUserDAO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private RatingDAO ratingDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private SettingsDAO settingsDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ReviewDAO reviewDAO;

    private UserFactory userFactory = new UserFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();
    private EpisodeFactory episodeFactory = new EpisodeFactory();
    private SettingFactory settingFactory = new SettingFactory();
    private RoleFactory roleFactory = new RoleFactory();
    private RatingFactory ratingFactory = new RatingFactory();
    private CommentFactory commentFactory = new CommentFactory();
    private ReviewFactory reviewFactory = new ReviewFactory();

    private User user = userFactory.getUser("aleks");
    private TvShow tvShow = tvShowFactory.getTvShow("test1");
    private TvShow tvShow2 = tvShowFactory.getTvShow("test2");
    private TvShow tvShow3 = tvShowFactory.getTvShow("test3");
    private Episode episode = episodeFactory.getEpisode("episode_1");
    private Episode episode2 = episodeFactory.getEpisode("episode_2");
    private Episode episode3 = episodeFactory.getEpisode("episode_3");
    private Rating rating = ratingFactory.getRating(5);
    private Role role1 = roleFactory.getRole("role_1");
    private Setting setting1 = settingFactory.getSetting("setting_1");
    private Comment comment = commentFactory.getComment("comment1");
    private Review review = reviewFactory.getReview("review1");


    @Before
    public void setDB() {

        userDAO.save(user);
        tvShowDAO.save(tvShow);
        tvShowDAO.save(tvShow2);
        tvShowDAO.save(tvShow3);
        episodeDAO.save(episode);
        episodeDAO.save(episode2);
        episodeDAO.save(episode3);
        ratingDAO.save(rating);
        roleDAO.save(role1);
        settingsDAO.save(setting1);
        commentDAO.save(comment);
        reviewDAO.save(review);
    }

    @Test
    public void addUserTest() {
        user.setName("Alex");
        user.setEmail("Alex@wp.pl");
        user.setPassword("1234");

        userDAO.save(user);

        Assert.assertEquals(user.getName(), userDAO.getById(user.getId()).getName());
        Assert.assertEquals(user.getEmail(), userDAO.getById(user.getId()).getEmail());
        Assert.assertEquals(user.getPassword(), userDAO.getById(user.getId()).getPassword());
        Assert.assertEquals(1, userDAO.getAll().size());
    }

    @Test
    public void updateUserTest() {
        user.setEmail("changeEmail");
        user.setPassword("changePassword");

        userDAO.update(user);

        Assert.assertEquals(user.getEmail(), "changeEmail");
        Assert.assertEquals(user.getPassword(), "changePassword");
    }

    @Test
    public void deleteUserTest() {
        userDAO.delete(user);

        Assert.assertEquals(null, userDAO.getById(user.getId()));
    }

    @Test
    public void getAllUsersTest() {
        int usersListSize = userDAO.getAll().size();

        Assert.assertEquals(1, usersListSize);
    }

    @Test
    public void getUserByIdTest() {
        User getUserByIdTest = userDAO.getById(user.getId());

        Assert.assertEquals(getUserByIdTest.getName(), user.getName());
    }

    @Test
    public void getUserByNameTest() {
        User getUserByNameTest = userDAO.getUserByName("Aleks");

        Assert.assertEquals(getUserByNameTest.getName(), user.getName());
    }

    @Test
    public void getUserByTokenTest() {
        user.setToken("Token");
        User test = userDAO.getUserByToken("Token");

        Assert.assertEquals(test.getName(), user.getName());
    }

    @Test
    public void getUserByEmailTest() {
        user.setEmail("Email");
        User test = userDAO.getUserByEmail("Email");

        Assert.assertEquals(test.getName(), user.getName());
    }

    @Test
    public void getTvShowsTest(){
        tvShow.getUsers().add(user);
        tvShowDAO.update(tvShow);
        user.getTvShows().add(tvShow);
        userDAO.update(user);

        Assert.assertEquals(userDAO.getTvShows(user),user.getTvShows());
    }

    @Test
    public void getEpisodesTest(){
        episode.getUsers().add(user);
        user.getEpisodes().add(episode);

        Assert.assertEquals(userDAO.getEpisodes(user),user.getEpisodes());
    }

    @Test
    public void getRolesTest(){
        role1.getUsers().add(user);
        user.getRoles().add(role1);

        Assert.assertEquals(userDAO.getRoles(user),user.getRoles());
    }

    @Test
    public void getRatingsTest(){
        rating.setUser(user);
        user.getRatings().add(rating);

        Assert.assertEquals(userDAO.getRatings(user),user.getRatings());
    }

    @Test
    public void getReviewsTest(){
        review.setUser(user);
        user.getReviews().add(review);

        Assert.assertEquals(userDAO.getReviews(user),user.getReviews());
    }

    @Test
    public void getCommentsTest(){
        comment.setUser(user);
        user.getComments().add(comment);

        Assert.assertEquals(userDAO.getComments(user),user.getComments());
    }
}