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

    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Test
    public void addUser2EpisodeTest() {
        int size = episode.getUsers().size();

        episodeDAO.addUser2Episode(user, episode);

        Assert.assertEquals(size + 1, episode.getUsers().size());
        Assert.assertEquals(size + 1, episodeDAO.getById(episode.getId()).getUsers().size());
        Assert.assertEquals(episodeDAO.getById(episode.getId()).getUsers().contains(user), true);
    }

    @Test
    public void deleteUserFromEpisode() {
        addUser2EpisodeTest();

        int size = episode.getUsers().size();

        episodeDAO.deleteUserFromEpisode(user, episode);

        Assert.assertEquals(size - 1, episode.getUsers().size());
        Assert.assertEquals(size - 1, episodeDAO.getById(episode.getId()).getUsers().size());
        Assert.assertEquals(episodeDAO.getById(episode.getId()).getUsers().contains(user), false);
    }

    //TvShow2Episode

    @Test
    public void addTvShow2EpisodeTest() {
        episodeDAO.addTvShow2Episode(tvShow, episode);

        Assert.assertEquals(episodeDAO.getById(episode.getId()).getTvShow().equals(tvShow), true);
    }

    @Test
    public void deleteTvShowFromEpisodeTest() {
        episodeDAO.deleteTvShowFromEpisode(episode);

        Assert.assertEquals(episodeDAO.getById(episode.getId()).getTvShow(), null);
    }

    @Test
    public void getUsersTest(){
        episodeDAO.addUser2Episode(user,episode);
        userDAO.addEpisode2User(user,episode);
        Assert.assertEquals(episodeDAO.getUsers(episode),episode.getUsers());
    }
}