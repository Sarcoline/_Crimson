package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EpisodeDAOImpl implements EpisodeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void save(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(episode);
    }

    @Override
    public void delete(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = userDAO.getAll();
        for (User user : users) {
            user.getEpisodes().remove(episode);
        }
        session.delete(episode);
    }

    @Override
    public void update(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        session.update(episode);
    }

    @Override
    public Episode getById(Long idEpisode) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Episode.class, idEpisode);
    }

    @Override
    public List<Episode> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Episode a", Episode.class).getResultList();
    }

    @Override
    public Episode getEpisodeByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Episode a where a.title like :custTitle", Episode.class)
                .setParameter("custTitle", title).getSingleResult();
    }


    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Override
    public void addUser2Episode(User user, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        if (!episode.getUsers().contains(user)) {
            List<User> episodes = new ArrayList<>();
            episodes.addAll(episode.getUsers());
            episodes.add(user);
            episode.setUsers(episodes);
        }
        session.saveOrUpdate(episode);
    }

    @Override
    public void deleteUserFromEpisode(User user, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        episode.getUsers().remove(user);
        session.saveOrUpdate(episode);
    }

    //TvShow2Episode

    @Override
    public void addTvShow2Episode(TvShow tvShow, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        episode.setTvShow(tvShow);
        session.saveOrUpdate(episode);
    }

    @Override
    public void deleteTvShowFromEpisode(TvShow tvShow, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        if (episode.getTvShow() == tvShow) {
            episode.setTvShow(null);
        }
        session.saveOrUpdate(episode);
    }

}
