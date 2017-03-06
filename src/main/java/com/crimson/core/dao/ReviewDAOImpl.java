package com.crimson.core.dao;


import com.crimson.core.model.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ReviewDAOImpl implements ReviewDAO{

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
    public List<Review> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT r FROM Review r", Review.class).getResultList();
    }

    @Override
    public Review getById(Long idReview) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Review.class, idReview);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> getReviewByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.user.id = ?";
        return session.createQuery(hql)
                .setParameter(0, idUser)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> getReviewByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.tvShow.id = ?";
        return session.createQuery(hql)
                .setParameter(0, idTvShow)
                .getResultList();
    }

    @Override
    public List getReviews(long idTvShow, long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Review r where r.tvShow.id = ? and r.user.id = ?";
        return session.createQuery(hql)
                .setParameter(0, idTvShow)
                .setParameter(1, idUser)
                .getResultList();
    }
}