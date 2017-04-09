package com.crimson.core.functional.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.EpisodeDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
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
@Rollback()
public class TestEpisodeDAO {


    @Autowired
    private EpisodeDAO episodeDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TvShowDAO tvShowDAO;

    private UserFactory userFactory = new UserFactory();
    private EpisodeFactory episodeFactory = new EpisodeFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();

    private Episode episode = episodeFactory.getEpisode("episode_1");
    private User user = userFactory.getUser("aleks");
    private TvShow tvShow = tvShowFactory.getTvShow("friends");


    @Before
    public void setDB() {
        episodeDAO.save(episode);
        tvShowDAO.save(tvShow);
        userDAO.save(user);
    }

    @Test
    public void addEpisodeTest() {
        episode.setTitle("Nowy");

        episodeDAO.save(episode);

        Assert.assertEquals(episode.getTitle(), episodeDAO.getById(episode.getId()).getTitle());
        Assert.assertEquals(episode.getNumber(), episodeDAO.getById(episode.getId()).getNumber());
        Assert.assertEquals(episode.getSeason(), episodeDAO.getById(episode.getId()).getSeason());
    }

    @Test
    public void updateEpisodeTest() {
        episode.setTitle("UpdatedTitle");
        episode.setSeason(2);

        episodeDAO.update(episode);

        Assert.assertEquals(episode.getTitle(), episodeDAO.getById(episode.getId()).getTitle());
        Assert.assertEquals(episode.getSeason(), episodeDAO.getById(episode.getId()).getSeason());
    }

    @Test
    public void deleteEpisodeTest() {
        episodeDAO.delete(episode);

        Assert.assertEquals(null, episodeDAO.getById(episode.getId()));
    }

    @Test
    public void getAllEpisodesTest() {
        int sizeList = episodeDAO.getAll().size();

        Assert.assertEquals(1, sizeList);
    }

    @Test
    public void getEpisodeByIdTest() {
        Episode test = episodeDAO.getById(episode.getId());

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
}