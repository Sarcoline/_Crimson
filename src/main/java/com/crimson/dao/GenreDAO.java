package com.crimson.dao;

import com.crimson.model.Genre;
import com.crimson.model.TvShow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class GenreDAO {

    @Autowired
    private SessionFactory sf;

    public void addGenre(Genre genre){
        Session session = sf.getCurrentSession();
        session.persist(genre);
    }

    public void  deleteGenre(Genre genre){
        Session session = sf.getCurrentSession();
        session.delete(genre);
    }

    public void updateGenre(Genre genre){
        Session session = sf.getCurrentSession();
        session.update(genre);
    }

    public Genre getGenreById(Long idGenre){
        Session session = sf.getCurrentSession();
        return session.find(Genre.class, idGenre);
    }

    public List<Genre> getAllGenre(){
        Session session = sf.getCurrentSession();
        List<Genre> genres = session.createQuery("SELECT a FROM Genre a", Genre.class).getResultList();
        return genres;
    }

    public Genre getGenreByName(String name){
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From Genre a where a.name like :custName", Genre.class).setParameter("custName", name).getSingleResult();
    }

    //RELATIONSHIPS

    //Genre2TvShow

    public void addTvShow2Genre(Genre genre, TvShow tvShow){
        if (!genre.getGenreTvShowList().contains(tvShow)){
            genre.getGenreTvShowList().add(tvShow);
        }
    }

    public void deleteTvShowFromGenre(Genre genre, TvShow tvShow){
        if (genre.getGenreTvShowList().contains(tvShow)){
            genre.getGenreTvShowList().remove(tvShow);
        }
    }
}
