package com.crimson.core.dao;

import com.crimson.core.model.Rating;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Rating getRatingByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Rating.class, idUser);
    }

    @Override
    public Rating getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Rating.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rating> getRatingByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Rating s where s.tvShow.id = ?";
        return session.createQuery(hql)
                .setParameter(0, idTvShow)
                .getResultList();
    }

    @Override
    public List<Rating> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM  Rating a", Rating.class).getResultList();
    }

    //podobno proper way?
    @Override
    public Rating getRating(long idtv, long iduser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Rating s where s.tvShow.id = ? and s.user.id = ?";
        List rating = session.createQuery(hql)
                .setParameter(0, idtv)
                .setParameter(1, iduser)
                .getResultList();
        return rating.isEmpty() ? new Rating() : (Rating) rating.get(0);
    }

}
