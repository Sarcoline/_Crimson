package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.model.Episode;
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
@Rollback(value = true)
public class TestEpisodeDAO {

    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    private Episode episode = new Episode();

    private User user = new User();

    private TvShow tvShow = new TvShow();

    @Before
    public void setDB() {
        episode.setTitle("Title");
        episode.setSeason("first");
        episode.setNumber(13);
        episode.setEpisodeSummary("Summary");

        episodeDAO.saveEpisode(episode);

        user.setName("AlexTheFrog");
        userDAO.saveUser(user);

        tvShow.setTitle("Gameów Of Alex");
        tvShow.setCountry("Poland");
        tvShow.setGenre("Drama");
        tvShow.setReleaseYear(2017);
        tvShow.setDescription("Test");
        tvShow.setNetwork("Bojano INC");
        tvShow.setOverallRating(7.1);
        tvShow.setTrailerUrl("google.pl");

        tvShowDAO.saveTvShow(tvShow);
    }

    @Test
    public void addEpisodeTest() {
        episode.setTitle("Nowy");

        episodeDAO.saveEpisode(episode);

        Assert.assertEquals(episode.getTitle(), episodeDAO.getEpisodeById(episode.getId()).getTitle());
        Assert.assertEquals(episode.getNumber(), episodeDAO.getEpisodeById(episode.getId()).getNumber());
        Assert.assertEquals(episode.getSeason(), episodeDAO.getEpisodeById(episode.getId()).getSeason());
    }

    @Test
    public void updateEpisodeTest() {
        episode.setTitle("UpdatedTitle");
        episode.setSeason("second");

        episodeDAO.updateEpisode(episode);

        Assert.assertEquals(episode.getTitle(), episodeDAO.getEpisodeById(episode.getId()).getTitle());
        Assert.assertEquals(episode.getSeason(), episodeDAO.getEpisodeById(episode.getId()).getSeason());
    }

    @Test
    public void deleteEpisodeTest() {
        episodeDAO.deleteEpisode(episode);

        Assert.assertEquals(null, episodeDAO.getEpisodeById(episode.getId()));
    }

    @Test
    public void getAllEpisodesTest() {
        int sizeList = episodeDAO.getAllEpisodes().size();

        Assert.assertEquals(1, sizeList);
    }

    @Test
    public void getEpisodeByIdTest() {
        Episode test = episodeDAO.getEpisodeById(episode.getId());

        Assert.assertEquals(test.getTitle(), episode.getTitle());
        Assert.assertEquals(test.getSeason(), episode.getSeason());
        Assert.assertEquals(test.getNumber(), episode.getNumber());
    }

    @Test
    public void getEpisodeByTitleTest() {
        Episode test = episodeDAO.getEpisodeByTitle(episode.getTitle());

        Assert.assertEquals(test.getId(), episode.getId());
        Assert.assertEquals(test.getSeason(), episode.getSeason());
        Assert.assertEquals(test.getNumber(), episode.getNumber());
    }

    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Test
    public void addUser2EpisodeTest() {
        int size = episode.getEpisodeUserList().size();

        episodeDAO.addUser2Episode(user, episode);

        Assert.assertEquals(size + 1, episode.getEpisodeUserList().size());
    }

    @Test
    public void deleteUserFromEpisode() {
        addUser2EpisodeTest();

        int size = episode.getEpisodeUserList().size();

        episodeDAO.deleteUserFromEpisode(user, episode);

        Assert.assertEquals(size - 1, episode.getEpisodeUserList().size());
    }

    //TvShow2Episode

    @Test
    public void addTvShow2EpisodeTest() {

        episodeDAO.addTvShow2Episode(tvShow, episode);

        assert (tvShow == episode.getEpisodeFromTvShow());
    }

    @Test
    public void deleteTvShowFromEpisodeTest() {
        episodeDAO.deleteTvShowFromEpisode(tvShow, episode);

        assert (episode.getEpisodeFromTvShow() == null);
    }
}
