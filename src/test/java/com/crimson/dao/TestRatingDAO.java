package com.crimson.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.model.Rating;
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
public class TestRatingDAO {

    @Autowired
    private RatingDAO ratingDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TvShowDAO tvShowDAO;

    private Rating rating = new Rating();
    private TvShow tvShow = new TvShow();
    private User user = new User();
    @Before
    public void setDB(){
        tvShow.setTitle("Gameów Of Alex");
        tvShow.setCountry("Poland");
        tvShow.setGenre("Drama");
        tvShow.setReleaseYear(2017);
        tvShow.setDescription("Test");
        tvShow.setNetwork("Bojano INC");
        tvShow.setOverallRating(7.1);
        tvShow.setTrailerUrl("google.pl");
        tvShowDAO.saveTvShow(tvShow);
        user.setName("Alex");
        userDAO.saveUser(user);
        rating.setValue(4);
        rating.setTvShowRating(tvShow);
        rating.setUserRating(user);
        ratingDAO.saveRating(rating);
    }

    @Test
    public void addRatingTest(){
        Assert.assertEquals(1, ratingDAO.getAllRatings().size());
        Rating rating1 = new Rating();
        rating1.setValue(5);
        rating1.setTvShowRating(tvShow);
        rating1.setUserRating(user);
        ratingDAO.saveRating(rating1);

        //Assert.assertEquals(rating1.getValue(),ratingDAO.getRatingByID(rating1.getId()).getValue());
    }

    @Test
    public void updateRatingTest(){
        rating.setValue(7);

        ratingDAO.updateRating(rating);

        Assert.assertEquals(rating.getValue(), ratingDAO.getRatingByID(rating.getId()).getValue());
    }

    @Test
    public void deleteRatingTest(){
        ratingDAO.deleteRating(rating);

        Assert.assertEquals(null,ratingDAO.getRatingByID(rating.getId()));
    }

    @Test
    public void getAllRatingsTest(){
        int sizeListRatings = ratingDAO.getAllRatings().size();

        Assert.assertEquals(1, sizeListRatings);
    }

    @Test
    public void getRatingByIdTest(){
        Rating ratingtest = ratingDAO.getRatingByID(rating.getId());

        Assert.assertEquals(ratingtest.getValue(), rating.getValue());
    }
}
