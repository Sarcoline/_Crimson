package com.crimson.core.dao;

import com.crimson.core.model.Rating;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RatingDAO {

    @Autowired
    private SessionFactory sf;

    public void saveRating(Rating rating) {
        Session session = sf.getCurrentSession();
        session.saveOrUpdate(rating);
    }

    public void deleteRating(Rating rating) {
        Session session = sf.getCurrentSession();
        session.delete(rating);
    }

    public void updateRating(Rating rating) {
        Session session = sf.getCurrentSession();
        session.update(rating);
    }

    public Rating getRatingByIdUser(Long idUser) {
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, idUser);
    }

    public Rating getRatingByID(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, id);
    }

    public Rating getRatingByIdTvShow(Long idTvShow) {
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, idTvShow);
    }

    public List<Rating> getAllRatings() {
        Session session = sf.getCurrentSession();
        return session.createQuery("SELECT a FROM  Rating a", Rating.class).getResultList();
    }

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
