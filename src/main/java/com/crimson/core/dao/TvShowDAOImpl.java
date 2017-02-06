package com.crimson.core.dao;

import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TvShowDAOImpl implements TvShowDAO {

    @Autowired
    private SessionFactory sf;

    @Override
    public void saveTvShow(TvShow tv) {
        Session session = sf.getCurrentSession();
        Slugify slg = new Slugify();
        tv.setSlug(slg.slugify(tv.getTitle()));
        session.persist(tv);
    }

    @Override
    public List<TvShow> getAllTvShows() {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a", TvShow.class).getResultList();
    }

    @Override
    public TvShow getTvById(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(TvShow.class, id);
    }

    @Override
    public TvShow getTvByIdWithEpisodes(Long id) {

        Session session = sf.getCurrentSession();

        TvShow tvshow = session.find(TvShow.class, id);
        Hibernate.initialize(tvshow.getEpisodes());

        return tvshow;
    }

    @Override
    public TvShow getTvBySlug(String slug) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class).setParameter("custSlug", slug).getSingleResult();
    }

    @Override
    public List<TvShow> getTvByGenre(String genre) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class).setParameter("custGenre", genre).getResultList();
    }

    @Override
    public void deleteTvShow(TvShow tvshow) {
        Session session = sf.getCurrentSession();
        session.delete(tvshow);
    }

    @Override
    public void updateTvShow(TvShow tvshow) {
        Session session = sf.getCurrentSession();
        session.update(tvshow);
    }

    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow) {
        if (!tvShow.getTvShowUserList().contains(user)) {
            tvShow.getTvShowUserList().add(user);
        }
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow) {
        if (tvShow.getTvShowUserList().contains(user)) {
            tvShow.getTvShowUserList().remove(user);
        }
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre) {
        if (!tvShow.getTvShowGenreList().contains(genre)) {
            tvShow.getTvShowGenreList().add(genre);
        }
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre) {
        if (tvShow.getTvShowGenreList().contains(genre)) {
            tvShow.getTvShowGenreList().remove(genre);
        }
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode) {
        if (!tvShow.getEpisodes().contains(episode)) {
            tvShow.getEpisodes().add(episode);
        }
    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode) {
        if (tvShow.getEpisodes().contains(episode)) {
            tvShow.getEpisodes().remove(episode);
        }
    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating) {
        if (!tvShow.getTvShowRating().contains(rating)) {
            tvShow.getTvShowRating().add(rating);
        }
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating) {
        if (tvShow.getTvShowRating().contains(rating)) {
            tvShow.getTvShowRating().remove(rating);
        }
    }


}