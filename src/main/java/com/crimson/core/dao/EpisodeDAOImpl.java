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

    @Override
    public void save(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(episode);
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
    @Cacheable("application-cache")
    public Episode getById(Long idEpisode) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Episode.class, idEpisode);
    }

    @Override
    @Cacheable("application-cache")
    public List<Episode> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Episode a", Episode.class).getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public Episode getEpisodeByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Episode a where a.title like :custTitle", Episode.class)
                .setParameter("custTitle", title).getSingleResult();
    }

    @Override
    @Cacheable("application-cache")
    public Episode getBySeasonAndEpisodeNumber(int season, int number, long idTv) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select e FROM Episode e where " +
                "e.tvShow.id = :idTv and e.number = :number and e.season = :season", Episode.class)
                .setParameter("idTv", idTv).setParameter("number", number).setParameter("season", season)
                .getSingleResult();

    }


    //RELATIONSHIPS

    //EpisodeWatched(User2Episode)

    @Override
    public void addUser2Episode(User user, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        episode.getUsers().add(user);
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
    public void deleteTvShowFromEpisode(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        episode.setTvShow(null);
        session.saveOrUpdate(episode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.episodes e where e.id = :id";
        return session.createQuery(hql)
                .setParameter("id", episode.getId())
                .getResultList();
    }

}
