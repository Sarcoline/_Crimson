package com.crimson.core.dao;

import com.crimson.context.TestSpringCore;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@WebAppConfiguration
@Transactional
@Rollback(value = true)
public class TestGenreDAO {

    @Autowired
    TvShowDAO tvShowDAO;
    @Autowired
    private GenreDAO genreDAO;
    private Genre genre = new Genre();

    private TvShow tvShow = new TvShow();

    @Before
    public void setDB() {
        genre.setName("Drama");

        genreDAO.addGenre(genre);

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
    public void addGenreTest() {
        genre.setName("Comedy");
        genreDAO.addGenre(genre);

        Assert.assertEquals(genre.getName(), genreDAO.getGenreById(genre.getId()).getName());
    }

    @Test
    public void updateGenreTest() {
        genre.setName("NEW_Drama");

        genreDAO.updateGenre(genre);
        Assert.assertEquals(genre.getName(), genreDAO.getGenreById(genre.getId()).getName());
    }

    @Test
    public void deleteGenreTest() {
        genreDAO.deleteGenre(genre);

        Assert.assertEquals(null, genreDAO.getGenreById(genre.getId()));
    }

    @Test
    public void getAllGenreTest() {
        int sizeList = genreDAO.getAllGenre().size();

        Assert.assertEquals(1, sizeList);
    }

    @Test
    public void getGenreByIdTest() {
        Genre testgenre = genreDAO.getGenreById(genre.getId());

        Assert.assertEquals(testgenre.getName(), genre.getName());
    }

    @Test
    public void getGenreByNameTest() {
        Genre test = genreDAO.getGenreByName(genre.getName());

        Assert.assertEquals(test.getId(), genre.getId());
    }


    //RELATIONSHIPS TEST

    //Genre2TvShow

    @Test
    public void addTvShow2GenreTest() {
        int size = genre.getGenreTvShowList().size();

        genreDAO.addTvShow2Genre(genre, tvShow);

        Assert.assertEquals(size + 1, genre.getGenreTvShowList().size());
    }

    @Test
    public void deleteTvShowFromGenre() {
        addTvShow2GenreTest();

        int size = genre.getGenreTvShowList().size();

        genreDAO.deleteTvShowFromGenre(genre, tvShow);

        Assert.assertEquals(size - 1, genre.getGenreTvShowList().size());
    }

}
