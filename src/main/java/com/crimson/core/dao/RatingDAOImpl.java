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
    private SessionFactory sf;

    @Override
    public void saveRating(Rating rating) {
        Session session = sf.getCurrentSession();
        session.saveOrUpdate(rating);
    }

    @Override
    public void deleteRating(Rating rating) {
        Session session = sf.getCurrentSession();
        session.delete(rating);
    }

    @Override
    public void updateRating(Rating rating) {
        Session session = sf.getCurrentSession();
        session.update(rating);
    }

    @Override
    public Rating getRatingByIdUser(Long idUser) {
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, idUser);
    }

    @Override
    public Rating getRatingByID(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, id);
    }

    @Override
    public List<Rating> getRatingByIdTvShow(Long idTvShow) {
        Session session = sf.getCurrentSession();
        String hql = "from Rating s where s.tvShowRating.id = ?";
        return session.createQuery(hql)
                .setParameter(0, idTvShow)
                .getResultList();
    }

    @Override
    public List<Rating> getAllRatings() {
        Session session = sf.getCurrentSession();
        return session.createQuery("SELECT a FROM  Rating a", Rating.class).getResultList();
    }

    @Override
    public Rating getRating(long idtv, long iduser) {
        Session session = sf.getCurrentSession();
        String hql = "from Rating s where s.tvShowRating.id = ? and s.userRating.id = ?";
        List rating = session.createQuery(hql)
                .setParameter(0, idtv)
                .setParameter(1, iduser)
                .getResultList();
        if (rating.size() != 0) return (Rating) rating.get(0);
        return new Rating();
    }
}
