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

    //Extra Methods

    @Test
    public void tvShowsLastPageNumber() {
        int lastPage = tvShowDAO.tvShowsLastPageNumber();

        Assert.assertEquals(lastPage, 1);
    }

    @Test
    public void tvShowsPaginationList() {
        List<TvShow> tvShows = tvShowDAO.tvShowsPaginationList(1);
        tvShows.addAll(tvShowDAO.tvShowsPaginationList(2));

        Assert.assertEquals(tvShows.get(0), tvShow);
        Assert.assertEquals(tvShows.get(1), tvShow2);
        Assert.assertEquals(tvShows.get(2), tvShow3);
        Assert.assertEquals(tvShows.get(3), tvShow4);
        Assert.assertEquals(tvShows.get(4), tvShow5);
        Assert.assertEquals(tvShows.get(5), tvShow6);
        Assert.assertEquals(tvShows.get(6), tvShow7);
        Assert.assertEquals(tvShows.get(7), tvShow8);
        Assert.assertEquals(tvShows.get(8), tvShow9);
        Assert.assertEquals(tvShows.get(9), tvShow10);

    }


    //RELATIONSHIP TESTS

    //User2TvShow
    @Test
    public void addUser2TvShow() {
        int size = tvShow.getUsers().size();

        tvShowDAO.addUser2TvShow(user, tvShow);

        Assert.assertEquals(size + 1, tvShow.getUsers().size());
        Assert.assertEquals(size + 1, tvShowDAO.getById(tvShow.getId()).getUsers().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getUsers().contains(user), true);
    }

    @Test
    public void deleteUserFromTvShow() {
        addUser2TvShow();

        int size = tvShow.getUsers().size();

        tvShowDAO.deleteUserFromTvShow(user, tvShow);

        Assert.assertEquals(size - 1, tvShow.getUsers().size());
        Assert.assertEquals(size - 1, tvShowDAO.getById(tvShow.getId()).getUsers().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getUsers().contains(user), false);
    }

    //Genre2TvShow

    @Test
    public void addGenre2TvShow() {
        int size = tvShow.getGenres().size();

        tvShowDAO.addGenre2TvShow(tvShow, genre);

        Assert.assertEquals(size + 1, tvShow.getGenres().size());
        Assert.assertEquals(size + 1, tvShowDAO.getById(tvShow.getId()).getGenres().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getGenres().contains(genre), true);
    }

    @Test
    public void deleteGenreFromTvShow() {
        addGenre2TvShow();

        int size = tvShow.getGenres().size();

        tvShowDAO.deleteGenreFromTvShow(tvShow, genre);

        Assert.assertEquals(size - 1, tvShow.getGenres().size());
        Assert.assertEquals(size - 1, tvShowDAO.getById(tvShow.getId()).getGenres().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getGenres().contains(genre), false);
    }

    //TvShow2Episode

    @Test
    public void addEpisode2TvShow() {
        int size = tvShow.getEpisodes().size();

        tvShowDAO.addEpisode2TvShow(tvShow, episode);

        Assert.assertEquals(size + 1, tvShow.getEpisodes().size());
        Assert.assertEquals(size + 1, tvShowDAO.getById(tvShow.getId()).getEpisodes().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getEpisodes().contains(episode), true);
    }

    @Test
    public void deleteEpisodeFromTvShow() {
        addEpisode2TvShow();

        int size = tvShow.getEpisodes().size();

        tvShowDAO.deleteEpisodeFromTvShow(tvShow, episode);

        Assert.assertEquals(size - 1, tvShow.getEpisodes().size());
        Assert.assertEquals(size - 1, tvShowDAO.getById(tvShow.getId()).getEpisodes().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getEpisodes().contains(episode), false);
    }

    @Test
    public void addRating2TvShow() {
        int size = tvShow.getRatings().size();

        tvShowDAO.addRating2TvShow(tvShow, rating);

        Assert.assertEquals(size + 1, tvShow.getRatings().size());
        Assert.assertEquals(size + 1, tvShowDAO.getById(tvShow.getId()).getRatings().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getRatings().contains(rating), true);
    }

    @Test
    public void deleteRatingFromTvShow() {
        addRating2TvShow();

        int size = tvShow.getRatings().size();

        tvShowDAO.deleteRatingFromTvShow(tvShow, rating);

        Assert.assertEquals(size - 1, tvShow.getRatings().size());
        Assert.assertEquals(size - 1, tvShowDAO.getById(tvShow.getId()).getRatings().size());
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getRatings().contains(rating), false);
    }

    @Test
    public void addCommentTest(){
        int listSize = tvShow.getComments().size();

        tvShowDAO.addComment(tvShow, comment);

        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getComments().contains(comment), true);
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getComments().size(), listSize+1);
    }

    @Test
    public void deleteCommentTest(){
        addCommentTest();

        int listSize = tvShow.getComments().size();
        tvShowDAO.deleteComment(tvShow, comment);

        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getComments().contains(comment), false);
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getComments().size(), listSize-1);
    }

    @Test
    public void addReviewTest(){
        int listSize = tvShowDAO.getReviews(tvShow).size();

        tvShowDAO.addReview(tvShow, review);
        reviewDAO.addTvShow2Review(review,tvShow);

        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getReviews().contains(review), true);
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getReviews().size(), listSize+1);
    }

    @Test
    public void deleteReviewTest(){
        addReviewTest();

        int listSize = tvShowDAO.getReviews(tvShow).size();
        tvShowDAO.deleteReview(tvShow, review);
        reviewDAO.deleteTvShowFromReview(review);

        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getReviews().contains(review), false);
        Assert.assertEquals(tvShowDAO.getById(tvShow.getId()).getReviews().size(), listSize-1);
    }

    @Test
    public void getUsersTest(){
        tvShowDAO.addUser2TvShow(user,tvShow);
        userDAO.addTvShow2User(user,tvShow);
        Assert.assertEquals(tvShowDAO.getUsers(tvShow),tvShow.getUsers());
    }

    @Test
    public void getGenresTest(){
        tvShowDAO.addGenre2TvShow(tvShow,genre);
        genreDAO.addTvShow2Genre(genre,tvShow);
        Assert.assertEquals(tvShowDAO.getGenres(tvShow),tvShow.getGenres());
    }

    @Test
    public void getEpisodesTest(){
        episodeDAO.addTvShow2Episode(tvShow,episode);
        episodeDAO.addTvShow2Episode(tvShow,episode);
        Assert.assertEquals(tvShowDAO.getEpisodes(tvShow),tvShow.getEpisodes());
    }

    @Test
    public void getRatingsTest(){
        tvShowDAO.addRating2TvShow(tvShow,rating);
        ratingDAO.addTvShow2Rating(rating,tvShow);
        Assert.assertEquals(tvShowDAO.getRatings(tvShow),tvShow.getRatings());
    }

    @Test
    public void getCommentsTest(){
        tvShowDAO.addComment(tvShow,comment);
        commentDAO.addTvShow2Comment(comment,tvShow);
        Assert.assertEquals(tvShowDAO.getComments(tvShow),tvShow.getComments());
    }

    @Test
    public void getReviewsTest(){
        tvShowDAO.addReview(tvShow,review);
        reviewDAO.addTvShow2Review(review,tvShow);
        Assert.assertEquals(tvShowDAO.getReviews(tvShow),tvShow.getReviews());
    }

}