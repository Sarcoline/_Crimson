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

    @Test
    public void getTvBySlug(){
        TvShow test = tvShowDAO.getTvBySlug(tvShow.getSlug());

        Assert.assertEquals(test.getSlug(),tvShow.getSlug());
        Assert.assertEquals(test,tvShow);
    }

    @Test
    public void getTvByGenre(){
        int size = tvShowDAO.getTvByGenre(tvShow.getGenre()).size();
        tvShow2.setGenre(tvShow.getGenre());
        List<TvShow> test = tvShowDAO.getTvByGenre(tvShow.getGenre());

        Assert.assertEquals(size + 1,test.size());
        Assert.assertEquals(true, test.contains(tvShow));
        Assert.assertEquals(true, test.contains(tvShow2));
    }

    @Test
    public void getTvByCountry(){
        int size = tvShowDAO.getTvByCountry(tvShow.getCountry()).size();
        tvShow2.setCountry(tvShow.getCountry());
        List<TvShow> test = tvShowDAO.getTvByCountry(tvShow.getCountry());

        Assert.assertEquals(size + 1,test.size());
        Assert.assertEquals(true, test.contains(tvShow));
        Assert.assertEquals(true, test.contains(tvShow2));
    }

    @Test
    public void getTvByYear(){
        int size = tvShowDAO.getTvByYear(2010).size();
        tvShow.setReleaseYear(2010);
        List<TvShow> test = tvShowDAO.getTvByYear(2010);

        Assert.assertEquals(size + 1,test.size());
        Assert.assertEquals(true, test.contains(tvShow));
    }

    @Test
    public void getTvByNetwork(){
        int size = tvShowDAO.getTvByNetwork(tvShow.getNetwork()).size();
        tvShow2.setNetwork(tvShow.getNetwork());
        List<TvShow> test = tvShowDAO.getTvByNetwork(tvShow.getNetwork());

        Assert.assertEquals(size + 1,test.size());
        Assert.assertEquals(true, test.contains(tvShow));
        Assert.assertEquals(true, test.contains(tvShow2));
    }

    @Test
    public void getTvShowsSize(){
        int size = tvShowDAO.getAll().size();
        tvShowDAO.delete(tvShow);
        List<TvShow> test = tvShowDAO.getAll();

        Assert.assertEquals(size - 1,test.size());
        Assert.assertEquals(false, test.contains(tvShow));
    }

    @Test
    public void getUsersTest(){
        tvShow.getUsers().add(user);
        tvShowDAO.update(tvShow);
        user.getTvShows().add(tvShow);
        userDAO.update(user);

        Assert.assertEquals(tvShowDAO.getUsers(tvShow),tvShow.getUsers());
    }

    @Test
    public void getGenresTest(){
        tvShow.getGenres().add(genre);
        tvShowDAO.update(tvShow);
        genre.getTvShows().add(tvShow);
        genreDAO.update(genre);

        Assert.assertEquals(tvShowDAO.getGenres(tvShow),tvShow.getGenres());
    }

    @Test
    public void getEpisodesTest(){
        episode.setIdTvShow(tvShow.getId());
        tvShow.getEpisodes().add(episode);

        Assert.assertEquals(tvShowDAO.getEpisodes(tvShow),tvShow.getEpisodes());
    }

    @Test
    public void getRatingsTest(){
        rating.setTvShow(tvShow);
        tvShow.getRatings().add(rating);

        Assert.assertEquals(tvShowDAO.getRatings(tvShow),tvShow.getRatings());
    }

    @Test
    public void getCommentsTest(){
        comment.setTvShow(tvShow);
        tvShow.getComments().add(comment);

        Assert.assertEquals(tvShowDAO.getComments(tvShow),tvShow.getComments());
    }

    @Test
    public void getReviewsTest(){
        review.setTvShow(tvShow);
        tvShow.getReviews().add(review);

        Assert.assertEquals(tvShowDAO.getReviews(tvShow),tvShow.getReviews());
    }
}