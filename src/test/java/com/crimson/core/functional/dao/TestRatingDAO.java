package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.factory.RatingFactory;
import com.crimson.core.factory.TvShowFactory;
import com.crimson.core.factory.UserFactory;
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
@Rollback()
public class TestRatingDAO {

    @Autowired
    private RatingDAO ratingDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TvShowDAO tvShowDAO;

    private RatingFactory ratingFactory = new RatingFactory();
    private UserFactory userFactory = new UserFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();

    private Rating rating = ratingFactory.getRating(5);
    private TvShow tvShow = tvShowFactory.getTvShow("friends");
    private User user = userFactory.getUser("aleks");

    @Before
    public void setDB() {
        tvShowDAO.save(tvShow);
        userDAO.save(user);
        ratingDAO.save(rating);
    }

    @Test
    public void addRatingTest() {
        Assert.assertEquals(1, ratingDAO.getAll().size());
        Rating rating1 = Rating.builder()
                .value(3)
                .build();
        rating1.setValue(5);
        rating1.setTvShow(tvShow);
        rating1.setUser(user);
        ratingDAO.save(rating1);

        //Assert.assertEquals(rating1.getValue(),ratingDAO.getRatingByID(rating1.getId()).getValue());
    }

    @Test
    public void updateRatingTest() {
        rating.setValue(7);

        ratingDAO.update(rating);

        Assert.assertEquals(rating.getValue(), ratingDAO.getById(rating.getId()).getValue());
    }

    @Test
    public void deleteRatingTest() {
        ratingDAO.delete(rating);

        Assert.assertEquals(null, ratingDAO.getById(rating.getId()));
    }

    @Test
    public void getAllRatingsTest() {
        int sizeListRatings = ratingDAO.getAll().size();

        Assert.assertEquals(1, sizeListRatings);
    }

    @Test
    public void getRatingByIdTest() {
        Rating ratingtest = ratingDAO.getById(rating.getId());

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
        tvShowDAO.save(tvShow);
        Rating rating = Rating.builder()
                .value(3)
                .build();
        rating.setTvShow(tvShow);
        rating.setUser(user);
        rating.setValue(6);
    }

    @Test
    public void addTvShow2CommentTest() {
        ratingDAO.addTvShow2Rating(rating,tvShow);

        Assert.assertEquals(ratingDAO.getById(rating.getId()).getTvShow().equals(tvShow), true);
    }

    @Test
    public void deleteTvShowFromCommentTest() {
        ratingDAO.deleteTvShowFromRating(rating);

        Assert.assertEquals(ratingDAO.getById(rating.getId()).getTvShow(), null);
    }

    @Test
    public void addUser2CommentTest() {
        ratingDAO.addUser2Rating(rating,user);

        Assert.assertEquals(ratingDAO.getById(rating.getId()).getUser().equals(user), true);
    }

    @Test
    public void deleteUserFromCommentTest() {
        ratingDAO.deleteUserFromRating(rating);

        Assert.assertEquals(ratingDAO.getById(rating.getId()).getUser(), null);
    }
}