package com.crimson.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.model.Rating;
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

    private Rating rating = new Rating();

    @Before
    public void setDB(){
        rating.setValue(4);
        rating.setId(Long.valueOf(1));

        ratingDAO.saveRating(rating);
    }

    @Test
    public void addRatingTest(){
        rating.setValue(5);

        ratingDAO.saveRating(rating);

        Assert.assertEquals(rating.getValue(),ratingDAO.getRatingByID(rating.getId()).getValue());
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
