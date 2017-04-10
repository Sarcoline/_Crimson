package com.crimson.core.dao;

import com.crimson.core.model.Rating;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingDAOImpl implements RatingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rating);
    }

    @Override
    public void delete(Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(rating);
    }

    @Override
    public void update(Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        session.update(rating);
    }

    @Override
    @Cacheable("application-cache")
    public Rating getRatingByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Rating.class, idUser);
    }

    @Override
    @Cacheable("application-cache")
    public Rating getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Rating.class, id);
    }

    @Override
    @Cacheable("application-cache")
    public List<Rating> getRatingByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Rating s where s.tvShow.id = :id";
        return session.createQuery(hql, Rating.class)
                .setParameter("id", idTvShow)
                .getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public List<Rating> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM  Rating a", Rating.class).getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public Rating getRating(long idtv, long iduser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Rating s where s.tvShow.id = :idtv and s.user.id = :idusr";
        List rating = session.createQuery(hql)
                .setParameter("idtv", idtv)
                .setParameter("idusr", iduser)
                .getResultList();
        return rating.isEmpty() ? new Rating() : (Rating) rating.get(0);
    }


}
