package com.crimson.core.dao;


import com.crimson.core.model.Review;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ReviewDAOImpl implements ReviewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(review);
    }

    @Override
    public void delete(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(review);
    }

    @Override
    public void update(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(review);
    }

    @Override
    @Cacheable("application-cache")
    public List<Review> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT r FROM Review r", Review.class).getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public Review getById(Long idReview) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Review.class, idReview);
    }

    @Override
    @Cacheable("application-cache")
    public List<Review> getReviewByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.user.id = :id";
        return session.createQuery(hql, Review.class)
                .setParameter("id", idUser)
                .getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public List<Review> getReviewByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.tvShow.id = :id";
        return session.createQuery(hql, Review.class)
                .setParameter("id", idTvShow)
                .getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public List<Review> getReviews(long idTvShow, long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.tvShow.id = :idtv and r.user.id = :idusr";
        return session.createQuery(hql, Review.class)
                .setParameter("idtv", idTvShow)
                .setParameter("idusr", idUser)
                .getResultList();
    }
}
