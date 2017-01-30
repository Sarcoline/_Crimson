package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDAOImpl implements GenreDAO {

    @Autowired
    private SessionFactory sf;

    @Override
    public void addGenre(Genre genre) {
        Session session = sf.getCurrentSession();
        session.persist(genre);
    }

    @Override
    public void deleteGenre(Genre genre) {
        Session session = sf.getCurrentSession();
        session.delete(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        Session session = sf.getCurrentSession();
        session.update(genre);
    }

    @Override
    public Genre getGenreById(Long idGenre) {
        Session session = sf.getCurrentSession();
        return session.find(Genre.class, idGenre);
    }

    @Override
    public List<Genre> getAllGenre() {
        Session session = sf.getCurrentSession();
        return session.createQuery("SELECT a FROM Genre a", Genre.class).getResultList();
    }

    @Override
    public Genre getGenreByName(String name) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From Genre a where a.name like :custName", Genre.class).setParameter("custName", name).getSingleResult();
    }

    //RELATIONSHIPS

    //Genre2TvShow

    @Override
    public void addTvShow2Genre(Genre genre, TvShow tvShow) {
        if (!genre.getGenreTvShowList().contains(tvShow)) {
            genre.getGenreTvShowList().add(tvShow);
        }
    }

    @Override
    public void deleteTvShowFromGenre(Genre genre, TvShow tvShow) {
        if (genre.getGenreTvShowList().contains(tvShow)) {
            genre.getGenreTvShowList().remove(tvShow);
        }
    }
}
