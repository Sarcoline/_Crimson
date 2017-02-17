package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import com.crimson.core.service.RatingService;
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
public class TestRatingDAO {

    @Autowired
    private RatingDAO ratingDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private RatingService ratingService;

    private Rating rating = Rating.builder()
            .value(5)
            .build();

    private TvShow tvShow = TvShow.builder()
            .title("Dr.House")
            .network("Netflix")
            .country("US")
            .genre("Drama")
            .build();

    private User user =  User.builder()
            .name("Aleks")
            .email("Email@wp.pl")
            .password("123")
            .build();

    @Before
    public void setDB() {
        tvShowDAO.saveTvShow(tvShow);
        userDAO.saveUser(user);
        ratingDAO.saveRating(rating);
    }

    @Test
    public void addRatingTest() {
        Assert.assertEquals(1, ratingDAO.getAllRatings().size());
        Rating rating1 = Rating.builder()
                .value(3)
                .build();
        rating1.setValue(5);
        rating1.setTvShowRating(tvShow);
        rating1.setUserRating(user);
        ratingDAO.saveRating(rating1);

        //Assert.assertEquals(rating1.getValue(),ratingDAO.getRatingByID(rating1.getId()).getValue());
    }

    @Test
    public void updateRatingTest() {
        rating.setValue(7);

        ratingDAO.updateRating(rating);

        Assert.assertEquals(rating.getValue(), ratingDAO.getRatingByID(rating.getId()).getValue());
    }

    @Test
    public void deleteRatingTest() {
        ratingDAO.deleteRating(rating);

        Assert.assertEquals(null, ratingDAO.getRatingByID(rating.getId()));
    }

    @Test
    public void getAllRatingsTest() {
        int sizeListRatings = ratingDAO.getAllRatings().size();

        Assert.assertEquals(1, sizeListRatings);
    }

    @Test
    public void getRatingByIdTest() {
        Rating ratingtest = ratingDAO.getRatingByID(rating.getId());

        Assert.assertEquals(ratingtest.getValue(), rating.getValue());
    }

    @Test
    public void getRating() {
        TvShow tvShow = TvShow.builder()
                .title("Dr.House")
                .network("Netflix")
                .country("US")
                .genre("Drama")
                .build();

        tvShow.setTitle("test");
        tvShowDAO.saveTvShow(tvShow);
        Rating rating = Rating.builder()
                .value(3)
                .build();
        rating.setTvShowRating(tvShow);
        rating.setUserRating(user);
        rating.setValue(6);
    }
}