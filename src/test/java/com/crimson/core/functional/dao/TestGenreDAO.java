package com.crimson.core.functional.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.GenreDAO;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.factory.GenreFactory;
import com.crimson.core.factory.TvShowFactory;
import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
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
public class TestGenreDAO {

    @Autowired
    private TvShowDAO tvShowDAO;
    @Autowired
    private GenreDAO genreDAO;

    private GenreFactory genreFactory = new GenreFactory();
    private TvShowFactory tvShowFactory = new TvShowFactory();

    private Genre genre = genreFactory.getGenre("drama");
    private TvShow tvShow = tvShowFactory.getTvShow("friends");


    @Before
    public void setDB() {
        genreDAO.save(genre);
        tvShowDAO.save(tvShow);
    }

    @Test
    public void addGenreTest() {
        genre.setName("Comedy");
        genreDAO.save(genre);

        Assert.assertEquals(genre.getName(), genreDAO.getById(genre.getId()).getName());
    }

    @Test
    public void updateGenreTest() {
        genre.setName("NEW_Drama");

        genreDAO.update(genre);
        Assert.assertEquals(genre.getName(), genreDAO.getById(genre.getId()).getName());
    }

    @Test
    public void deleteGenreTest() {
        genreDAO.delete(genre);

        Assert.assertEquals(null, genreDAO.getById(genre.getId()));
    }

    @Test
    public void getAllGenreTest() {
        int sizeList = genreDAO.getAll().size();

        Assert.assertEquals(1, sizeList);
    }

    @Test
    public void getGenreByIdTest() {
        Genre testgenre = genreDAO.getById(genre.getId());

        Assert.assertEquals(testgenre.getName(), genre.getName());
    }

    @Test
    public void getGenreByNameTest() {
        Genre test = genreDAO.getGenreByName(genre.getName());

        Assert.assertEquals(test.getId(), genre.getId());
    }

    @Test
    public void getTvShovsTest(){
        genre.getTvShows().add(tvShow);
        tvShow.getGenres().add(genre);

        Assert.assertEquals(genreDAO.getTvShows(genre),genre.getTvShows());
    }
}