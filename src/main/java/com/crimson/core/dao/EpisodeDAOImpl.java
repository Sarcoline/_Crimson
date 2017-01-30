package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EpisodeDAOImpl implements EpisodeDAO {

    @Autowired
    private SessionFactory sf;

    @Override
    public void saveEpisode(Episode episode) {
        Session session = sf.getCurrentSession();
        session.persist(episode);
    }

    @Override
    public void deleteEpisode(Episode episode) {
        Session session = sf.getCurrentSession();
        session.delete(episode);
    }

    @Override
    public void updateEpisode(Episode episode) {
        Session session = sf.getCurrentSession();
        session.update(episode);
    }

    @Override
    public Episode getEpisodeById(Long idEpisode) {
        Session session = sf.getCurrentSession();
        return session.find(Episode.class, idEpisode);
    }

    @Override
    public List<Episode> getAllEpisodes() {
        Session session = sf.getCurrentSession();
        return session.createQuery("SELECT a FROM Episode a", Episode.class).getResultList();
    }

    @Override
    public Episode getEpisodeByTitle(String title) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From Episode a where a.title like :custTitle", Episode.class).setParameter("custTitle", title).getSingleResult();
    }


    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Override
    public void addUser2Episode(User user, Episode episode) {
        if (!episode.getEpisodeUserList().contains(user)) {
            episode.getEpisodeUserList().add(user);
        }
    }

    @Override
    public void deleteUserFromEpisode(User user, Episode episode) {
        if (episode.getEpisodeUserList().contains(user)) {
            episode.getEpisodeUserList().remove(user);
        }
    }

    //TvShow2Episode

    @Override
    public void addTvShow2Episode(TvShow tvShow, Episode episode) {
        episode.setEpisodeFromTvShow(tvShow);
    }

    @Override
    public void deleteTvShowFromEpisode(TvShow tvShow, Episode episode) {
        if (episode.getEpisodeFromTvShow() == tvShow) {
            episode.setEpisodeFromTvShow(null);
        }
    }

}
