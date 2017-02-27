package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
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

    private UserFactory userFactory = new UserFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();
    private EpisodeFactory episodeFactory = new EpisodeFactory();
    private SettingFactory settingFactory = new SettingFactory();
    private RoleFactory roleFactory = new RoleFactory();
    private RatingFactory ratingFactory = new RatingFactory();
    private CommentFactory commentFactory = new CommentFactory();

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

    //Extra methods

//    @Test
//    public void getUserTvShowsSortedByMaxRating(){
//        userDAO.addTvShow2User(user, tvShow);
//        userDAO.addTvShow2User(user, tvShow2);
//        userDAO.addTvShow2User(user, tvShow3);
//        Rating rating = new Rating();
//        rating.setValue(5);
//        rating.setTvShow(tvShow);
//        rating.setUser(user);
//        Rating rating2 = new Rating();
//        rating2.setValue(8);
//        rating2.setTvShow(tvShow2);
//        rating2.setUser(user);
//        user.getRatings().add(rating);
//        user.getRatings().add(rating2);
//        List<TvShow> userTvShowList = userDAO.getUserTvShowsSortedByMaxRating(user);
//
//        Assert.assertEquals(userTvShowList.size(), 2);
//        Assert.assertEquals(user.getRatings().get(0).getValue(), rating.getValue());
//        Assert.assertEquals(user.getRatings().get(1).getValue(), rating2.getValue());
//    }
//
//    @Test
//    public void getAllUnwatchedEpisodes(){
//        userDAO.addEpisode2User(user, episode);
//        userDAO.addTvShow2User(user,tvShow);
//        tvShowDAO.addEpisode2TvShow(tvShow, episode);
//        tvShowDAO.addEpisode2TvShow(tvShow, episode2);
//        tvShowDAO.addEpisode2TvShow(tvShow, episode3);
//
//        List<Episode> allUnwatchedUserEpisodes = userDAO.getAllUnwatchedUserEpisodes(user);
//
//        Assert.assertEquals(allUnwatchedUserEpisodes.size(), 2);
//        Assert.assertEquals(allUnwatchedUserEpisodes.get(0).getTitle(), episode2.getTitle());
//        Assert.assertEquals(allUnwatchedUserEpisodes.get(1).getTitle(), episode3.getTitle());
//    }

    //RELATIONSHIP TESTS

    //User2TvShow
    @Test
    public void addTvShow2User() {
        int size = user.getTvShows().size();

        userDAO.addTvShow2User(user, tvShow);
        Assert.assertEquals(size + 1, user.getTvShows().size());
        Assert.assertEquals(size + 1, userDAO.getById(user.getId()).getTvShows().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getTvShows().contains(tvShow), true);
    }

    @Test
    public void deleteTvShowFromUser() {
        addTvShow2User();

        int size = user.getTvShows().size();

        userDAO.deleteTvShowFromUser(user, tvShow);

        Assert.assertEquals(size - 1, user.getTvShows().size());
        Assert.assertEquals(size - 1, userDAO.getById(user.getId()).getTvShows().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getTvShows().contains(tvShow), false);
    }

    //User2Episode

    @Test
    public void addEpisode2User() {
        int size = user.getEpisodes().size();

        userDAO.addEpisode2User(user, episode);

        Assert.assertEquals(size + 1, user.getEpisodes().size());
        Assert.assertEquals(size + 1, userDAO.getById(user.getId()).getEpisodes().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getEpisodes().contains(episode), true);
    }

    @Test
    public void deleteEpisodeFromUser() {
        addEpisode2User();

        int size = user.getEpisodes().size();

        userDAO.deleteEpisodeFromUser(user, episode);

        Assert.assertEquals(size - 1, user.getEpisodes().size());
        Assert.assertEquals(size - 1, userDAO.getById(user.getId()).getEpisodes().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getEpisodes().contains(episode), false);
    }

    //Rating
    @Test
    public void addRating2User() {
        int size = user.getRatings().size();

        userDAO.addRating2User(user, rating);

        Assert.assertEquals(size + 1, user.getRatings().size());
        Assert.assertEquals(size + 1, userDAO.getById(user.getId()).getRatings().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getRatings().contains(rating), true);
    }

    @Test
    public void deleteRatingFromUser() {
        addRating2User();

        int size = user.getRatings().size();

        userDAO.deleteRatingFromUser(user, rating);

        Assert.assertEquals(size - 1, user.getRatings().size());
        Assert.assertEquals(size - 1, userDAO.getById(user.getId()).getRatings().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getRatings().contains(rating), false);
    }

    //Setting
    @Test
    public void addSetting2UserTest() {
        Setting setting = user.getSetting();

        userDAO.addSetting2User(user, setting1);

        Assert.assertEquals(user.getSetting().equals(setting1), true);
        Assert.assertEquals(setting, null);
        Assert.assertEquals(userDAO.getById(user.getId()).getSetting().equals(setting1), true);
    }

    @Test
    public void deleteSettingFromUserTest() {
        addSetting2UserTest();

        userDAO.deleteSettingFromUser(user, setting1);

        Assert.assertEquals(user.getSetting(), null);
        Assert.assertEquals(userDAO.getById(user.getId()).getSetting(), null);
    }

    //User2Roles
    @Test
    public void addRole2UserTest() {
        int listSize = user.getRoles().size();

        userDAO.addRole2User(user, role1);

        Assert.assertEquals(user.getRoles().size(), listSize + 1);
        Assert.assertEquals(user.getRoles().contains(role1), true);
        Assert.assertEquals(userDAO.getById(user.getId()).getRoles().contains(role1), true);
        Assert.assertEquals(userDAO.getById(user.getId()).getRoles().size(), listSize + 1);
    }

    @Test
    public void deleteRoleFromUserTest() {
        addRole2UserTest();
        int listSize = user.getRoles().size();

        userDAO.deleteRoleFromUser(user, role1);

        Assert.assertEquals(listSize - 1, userDAO.getById(user.getId()).getRoles().size());
        Assert.assertEquals(userDAO.getById(user.getId()).getRoles().contains(role1), false);
    }

    //User2Comments

    @Test
    public void addCommentTest(){
        int listSize = user.getComments().size();
        userDAO.addComment(user, comment);
        Assert.assertEquals(userDAO.getById(user.getId()).getComments().contains(comment), true);
        Assert.assertEquals(userDAO.getById(user.getId()).getComments().size(), listSize+1);
    }

    @Test
    public void deleteCommentTest(){
        addCommentTest();
        int listSize = user.getComments().size();

        userDAO.deleteComment(user, comment);

        Assert.assertEquals(userDAO.getById(user.getId()).getComments().contains(comment), false);
        Assert.assertEquals(userDAO.getById(user.getId()).getComments().size(), listSize-1);
    }


}