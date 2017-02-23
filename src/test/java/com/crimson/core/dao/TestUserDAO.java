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

import java.util.List;

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

    private User user =  User.builder()
            .name("Aleks")
            .email("Email@wp.pl")
            .password("123")
            .version(1)
            .build();

    private TvShow tvShow = TvShow.builder()
            .title("Dr.House")
            .network("Netflix")
            .country("US")
            .genre("Drama")
            .overallRating(6.7)
            .build();

    private TvShow tvShow2 = TvShow.builder()
            .title("Dr.House")
            .network("Netflix")
            .country("US")
            .genre("Drama")
            .overallRating(8.7)
            .build();

    private TvShow tvShow3 = TvShow.builder()
            .title("Dr.House")
            .network("Netflix")
            .country("US")
            .genre("Drama")
            .overallRating(7.7)
            .build();

    private Episode episode = Episode.builder()
            .title("Episode 1")
            .build();

    private Episode episode2 = Episode.builder()
            .title("Episode 2")
            .build();

    private Episode episode3 = Episode.builder()
            .title("Episode 3")
            .build();

    private Rating rating = Rating.builder()
            .value(6)
            .build();


    @Before
    public void setDB() {

        userDAO.saveUser(user);
        tvShowDAO.saveTvShow(tvShow);
        tvShowDAO.saveTvShow(tvShow2);
        tvShowDAO.saveTvShow(tvShow3);
        episodeDAO.saveEpisode(episode);
        episodeDAO.saveEpisode(episode2);
        episodeDAO.saveEpisode(episode3);
        ratingDAO.saveRating(rating);
    }

    @Test
    public void addUserTest() {
        user.setName("Alex");
        user.setEmail("Alex@wp.pl");
        user.setPassword("1234");

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

    //Extra methods

    @Test
    public void getUserTvShowsSortedByMaxRating(){
        userDAO.addTvShow2User(user, tvShow);
        userDAO.addTvShow2User(user, tvShow2);
        userDAO.addTvShow2User(user, tvShow3);
        Rating rating = new Rating();
        rating.setValue(5);
        rating.setTvShowRating(tvShow);
        rating.setUserRating(user);
        Rating rating2 = new Rating();
        rating2.setValue(8);
        rating2.setTvShowRating(tvShow2);
        rating2.setUserRating(user);
        user.getUserRatings().add(rating);
        user.getUserRatings().add(rating2);
        List<TvShow> userTvShowList = userDAO.getUserTvShowsSortedByMaxRating(user);

        Assert.assertEquals(userTvShowList.size(), 2);
        Assert.assertEquals(user.getUserRatings().get(0).getValue(), rating.getValue());
        Assert.assertEquals(user.getUserRatings().get(1).getValue(), rating2.getValue());
    }

    @Test
    public void getAllUnwatchedEpisodes(){
        userDAO.addEpisode2User(user, episode);
        userDAO.addTvShow2User(user,tvShow);
        tvShowDAO.addEpisode2TvShow(tvShow, episode);
        tvShowDAO.addEpisode2TvShow(tvShow, episode2);
        tvShowDAO.addEpisode2TvShow(tvShow, episode3);

        List<Episode> allUnwatchedUserEpisodes = userDAO.getAllUnwatchedUserEpisodes(user);

        Assert.assertEquals(allUnwatchedUserEpisodes.size(), 2);
        Assert.assertEquals(allUnwatchedUserEpisodes.get(0).getTitle(), episode2.getTitle());
        Assert.assertEquals(allUnwatchedUserEpisodes.get(1).getTitle(), episode3.getTitle());
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