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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback()
public class TestTvShowDAO {

    @Autowired
    private TvShowDAO tvShowDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private RatingDAO ratingDAO;

    private TvShowFactory tvShowFactory = new TvShowFactory();
    private GenreFactory genreFactory = new GenreFactory();
    private EpisodeFactory episodeFactory = new EpisodeFactory();
    private UserFactory userFactory = new UserFactory();
    private RatingFactory ratingFactory = new RatingFactory();
    private CommentFactory commentFactory = new CommentFactory();
    private ReviewFactory reviewFactory = new ReviewFactory();

    private User user = userFactory.getUser("aleks");
    private Genre genre = genreFactory.getGenre("drama");
    private Episode episode = episodeFactory.getEpisode("episode_1");
    private Rating rating = ratingFactory.getRating(5);
    private Comment comment = commentFactory.getComment("comment1");
    private Review review = reviewFactory.getReview("review1");

    private TvShow tvShow = tvShowFactory.getTvShow("test1");
    private TvShow tvShow2 = tvShowFactory.getTvShow("test2");
    private TvShow tvShow3 = tvShowFactory.getTvShow("test3");
    private TvShow tvShow4 = tvShowFactory.getTvShow("test4");
    private TvShow tvShow5 = tvShowFactory.getTvShow("test5");
    private TvShow tvShow6 = tvShowFactory.getTvShow("test6");
    private TvShow tvShow7 = tvShowFactory.getTvShow("test7");
    private TvShow tvShow8 = tvShowFactory.getTvShow("test8");
    private TvShow tvShow9 = tvShowFactory.getTvShow("test9");
    private TvShow tvShow10 = tvShowFactory.getTvShow("test10");


    @Before
    public void setDB() {
        tvShowDAO.save(tvShow);
        tvShowDAO.save(tvShow2);
        tvShowDAO.save(tvShow3);
        tvShowDAO.save(tvShow4);
        tvShowDAO.save(tvShow5);
        tvShowDAO.save(tvShow6);
        tvShowDAO.save(tvShow7);
        tvShowDAO.save(tvShow8);
        tvShowDAO.save(tvShow9);
        tvShowDAO.save(tvShow10);

        genreDAO.save(genre);
        episodeDAO.save(episode);
        rating.setTvShow(tvShow);
        commentDAO.save(comment);
        reviewDAO.save(review);
    }

    @Test
    public void addTvShowTest() {
        tvShow.setTitle("AddTvShow");
        tvShow.setCountry("AddTvShow");
        tvShow.setGenre("AddTvShow");

        tvShowDAO.save(tvShow);

        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getById(tvShow.getId()).getTitle());
        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getById(tvShow.getId()).getCountry());
        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getById(tvShow.getId()).getGenre());
    }

    @Test
    public void updateTvShowTest() {
        tvShow.setTitle("UpdateTest");
        tvShow.setCountry("UpdateTest");

        tvShowDAO.update(tvShow);

        Assert.assertEquals(tvShow.getTitle(), tvShowDAO.getById(tvShow.getId()).getTitle());
        Assert.assertEquals(tvShow.getCountry(), tvShowDAO.getById(tvShow.getId()).getCountry());
    }

    @Test
    public void deleteTvShowTest() {
        tvShowDAO.delete(tvShow);

        Assert.assertEquals(null, tvShowDAO.getById(tvShow.getId()));
    }

    @Test
    public void getAllTvShowsTest() {
        int sizeListTvShows = tvShowDAO.getAll().size();

        Assert.assertEquals(10, sizeListTvShows);
    }

    @Test
    public void getTvShowByIdTest() {
        TvShow getTvShowByIdTest = tvShowDAO.getById(tvShow.getId());

        Assert.assertEquals(getTvShowByIdTest.getTitle(), tvShow.getTitle());
    }
}