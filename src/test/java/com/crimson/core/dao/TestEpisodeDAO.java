package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.factory.EpisodeFactory;
import com.crimson.core.factory.TvShowFactory;
import com.crimson.core.factory.UserFactory;
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

    private UserFactory userFactory = new UserFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();
    private EpisodeFactory episodeFactory = new EpisodeFactory();

    private User user = userFactory.getUser("Aleks");

    private TvShow tvShow = tvShowFactory.getTvShow("Dr.House");

    private Episode episode = episodeFactory.getEpisode("Episode 1");


    @Before
    public void setDB() {
        episodeDAO.saveEpisode(episode);
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
        episode.setSeason(2);

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