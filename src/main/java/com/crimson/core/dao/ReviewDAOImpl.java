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

    //Saving Review object to database
    @Override
    public void save(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(review);
    }

    //Deleting Review object from database
    @Override
    public void delete(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(review);
    }

    //Updating Review object from database
    @Override
    public void update(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(review);
    }

    //Getting Reviews objects from database and returning list
    @Override
    @Cacheable("application-cache")
    public List<Review> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT r FROM Review r", Review.class).getResultList();
    }

    //Getting Review object from database by id
    @Override
    @Cacheable("application-cache")
    public Review getById(Long idReview) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Review.class, idReview);
    }

    //Getting relational Review objects from database by relational user id
    @Override
    @Cacheable("application-cache")
    public List<Review> getReviewByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.user.id = :id";
        return session.createQuery(hql, Review.class)
                .setParameter("id", idUser)
                .getResultList();
    }

    //Getting relational Review objects from database by relational tvShow id
    @Override
    @Cacheable("application-cache")
    public List<Review> getReviewByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.tvShow.id = :id";
        return session.createQuery(hql, Review.class)
                .setParameter("id", idTvShow)
                .getResultList();
    }

    //Getting relational Reviews objects from database by User id and TvShow id
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
