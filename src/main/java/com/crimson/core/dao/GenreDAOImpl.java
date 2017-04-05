package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDAOImpl implements GenreDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(genre);
    }

    @Override
    public void delete(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(genre);
    }

    @Override
    public void update(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.update(genre);
    }

    @Override
    @Cacheable("application-cache")
    public Genre getById(Long idGenre) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Genre.class, idGenre);
    }

    @Override
    @Cacheable("application-cache")
    public List<Genre> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Genre a", Genre.class).getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public Genre getGenreByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Genre a where a.name like :custName", Genre.class)
                .setParameter("custName", name).getSingleResult();
    }

    //RELATIONSHIPS

    //Genre2TvShow

    @Override
    public void addTvShow2Genre(Genre genre, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        genre.getTvShows().add(tvShow);
        session.saveOrUpdate(genre);
    }

    @Override
    public void deleteTvShowFromGenre(Genre genre, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        genre.getTvShows().remove(tvShow);
        session.saveOrUpdate(genre);
    }

    @Override
    public List<TvShow> getTvShows(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TvShow t JOIN FETCH t.genres g where g.id = :id";
        return session.createQuery(hql, TvShow.class)
                .setParameter("id", genre.getId())
                .getResultList();
    }
}
