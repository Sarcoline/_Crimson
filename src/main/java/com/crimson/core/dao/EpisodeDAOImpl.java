package com.crimson.core.dao;

import com.crimson.core.model.Episode;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EpisodeDAOImpl implements EpisodeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDAO userDAO;

    //Saving Episode object to database
    @Override
    public void save(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(episode);
    }

    //Deleting Episode object from database
    @Override
    public void delete(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = userDAO.getAll();
        for (User user : users) {
            user.getEpisodes().remove(episode);
        }
        session.delete(episode);
    }

    //Updating Episode object from database
    @Override
    public void update(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        session.update(episode);
    }

    //Getting Episode object from database by id
    @Override
    @Cacheable("application-cache")
    public Episode getById(Long idEpisode) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Episode.class, idEpisode);
    }

    //Getting Episodes objects from database and returning list
    @Override
    @Cacheable("application-cache")
    public List<Episode> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Episode a", Episode.class).getResultList();
    }

    //Getting Episode object from database by title
    @Override
    @Cacheable("application-cache")
    public Episode getEpisodeByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Episode a where a.title like :custTitle", Episode.class)
                .setParameter("custTitle", title).getSingleResult();
    }

    //Getting Episode object from database by season and episode number
    @Override
    @Cacheable("application-cache")
    public Episode getBySeasonAndEpisodeNumber(int season, int number, long idTv) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select e FROM Episode e where " +
                "e.tvShow.id = :idTv and e.number = :number and e.season = :season", Episode.class)
                .setParameter("idTv", idTv).setParameter("number", number).setParameter("season", season)
                .getSingleResult();
    }

    //Getting relational Users objects from Episode object from database
    @Override
    public List<User> getUsers(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.episodes e where e.id = :id";
        return session.createQuery(hql, User.class)
                .setParameter("id", episode.getId())
                .getResultList();
    }

}
