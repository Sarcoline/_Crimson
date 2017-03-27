package com.crimson.core.functional.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.ReviewDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.factory.ReviewFactory;
import com.crimson.core.factory.TvShowFactory;
import com.crimson.core.factory.UserFactory;
import com.crimson.core.model.Review;
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
public class TestReviewDAO {

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private UserDAO userDAO;

    private ReviewFactory reviewFactory = new ReviewFactory();
    private UserFactory userFactory = new UserFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();

    private Review review1 = reviewFactory.getReview("review1");
    private Review review2 = reviewFactory.getReview("review2");
    private User user = userFactory.getUser("aleks");
    private TvShow tvShow = tvShowFactory.getTvShow("friends");

    @Before
    public void setDB(){
        reviewDAO.save(review1);
        reviewDAO.save(review2);
        userDAO.save(user);
        tvShowDAO.save(tvShow);
    }

    @Test
    public void saveTest(){
        Review review = Review.builder()
                .title("TEST")
                .introduction("TESTING")
                .content("TEST")
                .build();
        int listSize = reviewDAO.getAll().size();

        reviewDAO.save(review);

        Assert.assertEquals(listSize+1, reviewDAO.getAll().size());
        Assert.assertEquals(reviewDAO.getAll().contains(review), true);
    }

    @Test
    public void updateTest(){
        Review review = reviewDAO.getById(reviewDAO.getAll().get(0).getId());
        review.setTitle("CHANGED");

        reviewDAO.update(review);

        Assert.assertEquals(reviewDAO.getAll().get(0).getTitle(), review.getTitle());
    }

    @Test
    public void deleteTest(){
        int listSize = reviewDAO.getAll().size();

        reviewDAO.delete(review2);

        Assert.assertEquals(listSize-1, reviewDAO.getAll().size());
        Assert.assertEquals(reviewDAO.getAll().contains(review2), false);
    }

    @Test
    public void getAllReviewsTest(){
        Assert.assertEquals(reviewDAO.getAll().size(), 2);
    }

    @Test
    public void getReviewByIdTest(){
        Review review = reviewDAO.getById(review1.getId());

        Assert.assertEquals(review.equals(review1), true);
    }

    @Test
    public void getReviewByIdTvTest(){
        review1.setTvShow(tvShow);
        review2.setTvShow(tvShow);

        List<Review> ByTv = reviewDAO.getReviewByIdTvShow(tvShow.getId());

        Assert.assertEquals(ByTv.contains(review1), true);
        Assert.assertEquals(ByTv.contains(review2), true);
    }

    @Test
    public void getReviewByIdUserTest(){
        review1.setUser(user);
        review2.setUser(user);

        List<Review> ByUser = reviewDAO.getReviewByIdUser(user.getId());

        Assert.assertEquals(ByUser.contains(review1), true);
        Assert.assertEquals(ByUser.contains(review2), true);
    }

    @Test
    public void getReviewsTest(){
        review1.setUser(user);
        review1.setTvShow(tvShow);

        review2.setUser(user);
        review2.setTvShow(tvShow);

        List<Review> reviewList = reviewDAO.getReviews(tvShow.getId(), user.getId());

        Assert.assertEquals(reviewList.contains(review1), true);
        Assert.assertEquals(reviewList.contains(review2), true);
    }

    @Test
    public void addTvShow2CommentTest() {
        reviewDAO.addTvShow2Review(review1,tvShow);

        Assert.assertEquals(reviewDAO.getById(review1.getId()).getTvShow().equals(tvShow), true);
    }

    @Test
    public void deleteTvShowFromCommentTest() {
        reviewDAO.deleteTvShowFromReview(review1);

        Assert.assertEquals(reviewDAO.getById(review1.getId()).getTvShow(), null);
    }

    @Test
    public void addUser2CommentTest() {
        reviewDAO.addUser2Review(review1,user);

        Assert.assertEquals(reviewDAO.getById(review1.getId()).getUser().equals(user), true);
    }

    @Test
    public void deleteUserFromCommentTest() {
        reviewDAO.deleteUserFromReview(review1);

        Assert.assertEquals(reviewDAO.getById(review1.getId()).getUser(), null);
    }

}
