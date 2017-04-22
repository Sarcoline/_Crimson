package com.crimson.core.unit.service;

import com.crimson.core.dao.*;
import com.crimson.core.dto.TvShowDTO;
import com.crimson.core.dto.TvShowEditDTO;
import com.crimson.core.model.*;
import com.crimson.core.service.TvShowServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestTvShowService {

    @Mock
    private TvShowDAO tvShowDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private GenreDAO genreDAO;
    @Mock
    private EpisodeDAO episodeDAO;
    @Mock
    private RatingDAO ratingDAO;
    @Mock
    private CommentDAO commentDAO;
    @Mock
    private ReviewDAO reviewDAO;
    @Mock
    private MapperFacade mapperFacade;

    @InjectMocks
    private TvShowServiceImpl tvShowService = new TvShowServiceImpl();

    @Test
    public void getAllTest(){
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(tvShowDAO.getAll()).thenReturn(tvs);

        tvShowService.getAllTvShows();

        Mockito.verify(tvShowDAO).getAll();
    }

    @Test
    public void saveTest(){
        TvShow tv = TvShow.builder().build();

        doNothing().when(tvShowDAO).save(anyObject());

        tvShowService.saveTvShow(tv);

        Mockito.verify(tvShowDAO).save(anyObject());
    }

    @Test
    public void updateTest(){
        TvShow tv = TvShow.builder().build();
        TvShowEditDTO tvDTO = new TvShowEditDTO();
        tvDTO.setTitle("Title");
        tvDTO.setCountry("Country");
        tvDTO.setNetwork("Network");
        when(tvShowDAO.getById(anyLong())).thenReturn(tv);
        doNothing().when(tvShowDAO).update(anyObject());

        tvShowService.updateTvShow(tvDTO);

        Mockito.verify(tvShowDAO).update(anyObject());
        Mockito.verify(tvShowDAO).getById(anyLong());
    }

    @Test
    public void getByIdTest(){
        TvShow tv = TvShow.builder().build();

        when(tvShowDAO.getById(anyLong())).thenReturn(tv);

        tvShowService.getTvById(1L);

        Mockito.verify(tvShowDAO).getById(anyLong());
    }

    @Test
    public void getByIdWithEpisodesTest(){
        TvShow tv = TvShow.builder().build();

        when(tvShowDAO.getTvByIdWithEpisodes(anyLong())).thenReturn(tv);

        tvShowService.getTvByIdWithEpisodes(1L);

        Mockito.verify(tvShowDAO).getTvByIdWithEpisodes(anyLong());
    }

    @Test
    public void getBySlugTest(){
        TvShow tv = TvShow.builder().build();

        when(tvShowDAO.getTvBySlug(anyString())).thenReturn(tv);

        tvShowService.getTvBySlug("Slug");

        Mockito.verify(tvShowDAO).getTvBySlug(anyString());
    }

    @Test
    public void getByGenreTest(){
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(tvShowDAO.getTvByGenre(anyString())).thenReturn(tvs);

        tvShowService.getTvByGenre("Test");

        Mockito.verify(tvShowDAO).getTvByGenre(anyString());
    }

    @Test
    public void getByCountryTest(){
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(tvShowDAO.getTvByCountry(anyString())).thenReturn(tvs);

        tvShowService.getTvByCountry("Test");

        Mockito.verify(tvShowDAO).getTvByCountry(anyString());
    }

    @Test
    public void getByYearTest(){
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(tvShowDAO.getTvByYear(anyInt())).thenReturn(tvs);

        tvShowService.getTvByYear(1999);

        Mockito.verify(tvShowDAO).getTvByYear(anyInt());
    }

    @Test
    public void getByNetworkTest(){
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);

        when(tvShowDAO.getTvByNetwork(anyString())).thenReturn(tvs);

        tvShowService.getTvByNetwork("Test");

        Mockito.verify(tvShowDAO).getTvByNetwork(anyString());
    }

    @Test
    public void deleteTest(){
        TvShow tv = TvShow.builder().build();

        when(tvShowDAO.getTvBySlug(anyString())).thenReturn(tv);
        doNothing().when(tvShowDAO).delete(anyObject());

        tvShowService.deleteTvShow("Test");

        Mockito.verify(tvShowDAO).getTvBySlug(anyString());
        Mockito.verify(tvShowDAO).delete(anyObject());
    }

    @Test
    public void sizeTest(){
        when(tvShowDAO.tvShowsSize()).thenReturn(5L);

        tvShowService.tvShowsSize();

        Mockito.verify(tvShowDAO).tvShowsSize();
    }

    @Test
    public void addTvShow2UserTest(){
        User user = User.builder().name("Test").build();
        TvShow tv = TvShow.builder().build();

        doNothing().when(userDAO).update(anyObject());
        doNothing().when(tvShowDAO).update(anyObject());

        tvShowService.addUser2TvShow(user,tv);

        Mockito.verify(userDAO,Mockito.times(1)).update(anyObject());
        Mockito.verify(tvShowDAO,Mockito.times(1)).update(anyObject());
    }

    @Test
    public void deleteTvShowFromUserTest(){
        User user = User.builder().name("Test").build();
        TvShow tv = TvShow.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userDAO.getTvShows(anyObject())).thenReturn(tvs);
        when(tvShowDAO.getUsers(anyObject())).thenReturn(users);
        doNothing().when(userDAO).update(anyObject());
        doNothing().when(tvShowDAO).update(anyObject());

        tvShowService.deleteUserFromTvShow(user,tv);

        Mockito.verify(userDAO,Mockito.times(1)).update(anyObject());
        Mockito.verify(tvShowDAO,Mockito.times(1)).update(anyObject());
    }

    @Test
    public void addTvShow2GenreTest(){
        TvShow tv = TvShow.builder().build();
        Genre genre = Genre.builder().build();

        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(genreDAO).update(anyObject());

        tvShowService.addGenre2TvShow(tv,genre);

        Mockito.verify(genreDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void deleteTvShow2GenreTest(){
        TvShow tv = TvShow.builder().build();
        Genre genre = Genre.builder().build();
        List<TvShow> tvs = new ArrayList<>();
        tvs.add(tv);
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);

        when(genreDAO.getTvShows(anyObject())).thenReturn(tvs);
        when(tvShowDAO.getGenres(anyObject())).thenReturn(genres);
        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(genreDAO).update(anyObject());

        tvShowService.deleteGenreFromTvShow(tv,genre);

        Mockito.verify(genreDAO,times(2)).getTvShows(anyObject());
        Mockito.verify(tvShowDAO,times(2)).getGenres(anyObject());
        Mockito.verify(genreDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void addTvShow2EpisodeTest(){
        TvShow tv = TvShow.builder().build();
        Episode episode = Episode.builder().build();

        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(episodeDAO).update(anyObject());

        tvShowService.addEpisode2TvShow(tv,episode);

        Mockito.verify(episodeDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void deleteTvShow2EpisodeTest(){
        TvShow tv = TvShow.builder().build();
        Episode episode = Episode.builder().build();
        episode.setTvShow(tv);
        List<Episode> episodes = new ArrayList<>();
        episodes.add(episode);

        when(tvShowDAO.getEpisodes(anyObject())).thenReturn(episodes);
        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(episodeDAO).update(anyObject());

        tvShowService.deleteEpisodeFromTvShow(tv,episode);

        Mockito.verify(tvShowDAO,times(2)).getEpisodes(anyObject());
        Mockito.verify(episodeDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void addTvShow2RatingTest(){
        TvShow tv = TvShow.builder().build();
        Rating rating = Rating.builder().build();

        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(ratingDAO).update(anyObject());

        tvShowService.addRating2TvShow(tv,rating);

        Mockito.verify(ratingDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void deleteTvShow2RatingTest(){
        TvShow tv = TvShow.builder().build();
        Rating rating = Rating.builder().build();
        rating.setTvShow(tv);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        when(tvShowDAO.getRatings(anyObject())).thenReturn(ratings);
        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(ratingDAO).update(anyObject());

        tvShowService.deleteRatingFromTvShow(tv,rating);

        Mockito.verify(tvShowDAO,times(2)).getRatings(anyObject());
        Mockito.verify(ratingDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void addTvShow2CommentTest(){
        TvShow tv = TvShow.builder().build();
        Comment comment = Comment.builder().build();

        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(commentDAO).update(anyObject());

        tvShowService.addComment(tv,comment);

        Mockito.verify(commentDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void deleteTvShow2CommentTest(){
        TvShow tv = TvShow.builder().build();
        Comment comment = Comment.builder().build();
        comment.setTvShow(tv);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        when(tvShowDAO.getComments(anyObject())).thenReturn(comments);
        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(commentDAO).update(anyObject());

        tvShowService.deleteComment(tv,comment);

        Mockito.verify(tvShowDAO,times(2)).getComments(anyObject());
        Mockito.verify(commentDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void addTvShow2ReviewTest(){
        TvShow tv = TvShow.builder().build();
        Review review = Review.builder().build();

        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(reviewDAO).update(anyObject());

        tvShowService.addReview(tv,review);

        Mockito.verify(reviewDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void deleteTvShow2ReviewTest(){
        TvShow tv = TvShow.builder().build();
        Review review = Review.builder().build();
        review.setTvShow(tv);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(tvShowDAO.getReviews(anyObject())).thenReturn(reviews);
        doNothing().when(tvShowDAO).update(anyObject());
        doNothing().when(reviewDAO).update(anyObject());

        tvShowService.deleteReview(tv,review);

        Mockito.verify(tvShowDAO,times(2)).getReviews(anyObject());
        Mockito.verify(reviewDAO).update(anyObject());
        Mockito.verify(tvShowDAO).update(anyObject());
    }

    @Test
    public void getUsersTest(){
        TvShow tv = TvShow.builder().build();
        User user = User.builder().build();
        List<User> test = new ArrayList<>();
        test.add(user);

        when(tvShowDAO.getUsers(anyObject())).thenReturn(test);

        tvShowService.getUsers(tv);

        Mockito.verify(tvShowDAO).getUsers(anyObject());
    }

    @Test
    public void getGenresTest(){
        TvShow tv = TvShow.builder().build();
        Genre genre = Genre.builder().build();
        List<Genre> test = new ArrayList<>();
        test.add(genre);

        when(tvShowDAO.getGenres(anyObject())).thenReturn(test);

        tvShowService.getGenres(tv);

        Mockito.verify(tvShowDAO).getGenres(anyObject());
    }

    @Test
    public void getEpisodesTest(){
        TvShow tv = TvShow.builder().build();
        Episode episode = Episode.builder().build();
        List<Episode> test = new ArrayList<>();
        test.add(episode);

        when(tvShowDAO.getEpisodes(anyObject())).thenReturn(test);

        tvShowService.getEpisodes(tv);

        Mockito.verify(tvShowDAO).getEpisodes(anyObject());
    }

    @Test
    public void getRatingsTest(){
        TvShow tv = TvShow.builder().build();
        Rating rating = Rating.builder().build();
        List<Rating> test = new ArrayList<>();
        test.add(rating);

        when(tvShowDAO.getRatings(anyObject())).thenReturn(test);

        tvShowService.getRatings(tv);

        Mockito.verify(tvShowDAO).getRatings(anyObject());
    }

    @Test
    public void getCommentsTest(){
        TvShow tv = TvShow.builder().build();
        Comment comment = Comment.builder().build();
        List<Comment> test = new ArrayList<>();
        test.add(comment);

        when(tvShowDAO.getComments(anyObject())).thenReturn(test);

        tvShowService.getComments(tv);

        Mockito.verify(tvShowDAO).getComments(anyObject());
    }

    @Test
    public void getReviewsTest(){
        TvShow tv = TvShow.builder().build();
        Review review = Review.builder().build();
        List<Review> test = new ArrayList<>();
        test.add(review);

        when(tvShowDAO.getReviews(anyObject())).thenReturn(test);

        tvShowService.getReviews(tv);

        Mockito.verify(tvShowDAO).getReviews(anyObject());
    }
}
