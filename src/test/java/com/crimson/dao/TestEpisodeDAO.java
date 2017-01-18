package com.crimson.dao;

import com.crimson.context.SpringCore;
import com.crimson.context.TestSpringCore;
import com.crimson.context.WebConfig;
import com.crimson.model.Episode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback(value = true)
public class TestEpisodeDAO {

    @Autowired
    private EpisodeDAO episodeDAO;

    private Episode episode = new Episode();

    @Before
    public void setDB(){
        episode.setTitle("Title");
        episode.setSeason("first");
        episode.setNumber(13);
        episode.setEpisodeSummary("Summary");

        episodeDAO.saveEpisode(episode);
    }

    @Test
    public void addEpisodeTest(){
        episode.setTitle("Nowy");

        episodeDAO.saveEpisode(episode);

        Assert.assertEquals(episode.getTitle(),episodeDAO.getEpisodeById(episode.getId()).getTitle());
        Assert.assertEquals(episode.getNumber(),episodeDAO.getEpisodeById(episode.getId()).getNumber());
        Assert.assertEquals(episode.getSeason(),episodeDAO.getEpisodeById(episode.getId()).getSeason());
    }

    @Test
    public void updateEpisodeTest(){
        episode.setTitle("UpdatedTitle");
        episode.setSeason("second");

        episodeDAO.updateEpisode(episode);

        Assert.assertEquals(episode.getTitle(),episodeDAO.getEpisodeById(episode.getId()).getTitle());
        Assert.assertEquals(episode.getSeason(),episodeDAO.getEpisodeById(episode.getId()).getSeason());
    }

    @Test
    public void deleteEpisodeTest(){
        episodeDAO.deleteEpisode(episode);

        Assert.assertEquals(null,episodeDAO.getEpisodeById(episode.getId()));
    }

    @Test
    public void getAllEpisodesTest(){
        int sizeList = episodeDAO.getAllEpisodes().size();

        Assert.assertEquals(1, sizeList);
    }

    @Test
    public void getEpisodeByIdTest(){
        Episode test = episodeDAO.getEpisodeById(episode.getId());

        Assert.assertEquals(test.getTitle(), episode.getTitle());
        Assert.assertEquals(test.getSeason(),episode.getSeason());
        Assert.assertEquals(test.getNumber(),episode.getNumber());
    }

    @Test
    public void getEpisodeByTitleTest(){
        Episode test = episodeDAO.getEpisodeByTitle(episode.getTitle());

        Assert.assertEquals(test.getId(), episode.getId());
        Assert.assertEquals(test.getSeason(),episode.getSeason());
        Assert.assertEquals(test.getNumber(),episode.getNumber());
    }
}
