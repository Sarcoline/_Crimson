package com.crimson.core.dao;

import com.crimson.core.model.Genre;
import com.crimson.core.model.TvShow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class GenreDAOImpl implements GenreDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //Saving Genre object to database
    @Override
    public void save(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(genre);
    }

    //Deleting Genre object from database
    @Override
    public void delete(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(genre);
    }

    //Updating Genre object from database
    @Override
    public void update(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.update(genre);
    }

    //Getting Genre object from database by id
    @Override
    @Cacheable("application-cache")
    public Genre getById(Long idGenre) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Genre.class, idGenre);
    }

    //Getting Genres objects from database and returning list
    @Override
    @Cacheable("application-cache")
    public List<Genre> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Genre a", Genre.class).getResultList();
    }

    //Getting Genre object from database by name
    @Override
    @Cacheable("application-cache")
    public Genre getGenreByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Genre a where a.name like :custName", Genre.class)
                .setParameter("custName", name).getSingleResult();
    }

    //Getting relational TvShows objects from Genre object from database
    @Override
    public List<TvShow> getTvShows(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TvShow t JOIN FETCH t.genres g where g.id = :id";
        return session.createQuery(hql, TvShow.class)
                .setParameter("id", genre.getId())
                .getResultList();
    }

    @Override
    public long GenreSize() {

        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT count(x) FROM Genre x");

        return (long) q.getSingleResult();
    }


}
