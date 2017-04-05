package com.crimson.core.dao;


import com.crimson.core.model.Comment;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(comment);
    }

    @Override
    public void delete(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(comment);
    }

    @Override
    public void update(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(comment);
    }

    @Override
    @Cacheable("application-cache")
    public Comment getById(Long idComment) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Comment.class, idComment);
    }

    @Override
    @Cacheable("application-cache")
    public List<Comment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Comment a", Comment.class).getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public List<Comment> getCommentsByDate(LocalDate date) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Comment a where a.date like :custDate", Comment.class).setParameter("custDate", date).getResultList();
    }

    @Override
    public void addTvShow2Comment(Comment comment, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        comment.setTvShow(tvShow);
        session.saveOrUpdate(comment);
    }

    @Override
    public void deleteTvShowFromComment(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        comment.setTvShow(null);
        session.saveOrUpdate(comment);
    }

    @Override
    public void addUser2Comment(Comment comment, User user) {
        Session session = sessionFactory.getCurrentSession();
        comment.setUser(user);
        session.saveOrUpdate(comment);
    }

    @Override
    public void deleteUserFromComment(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        comment.setUser(null);
        session.saveOrUpdate(comment);
    }

    @Override
    @Cacheable("application-cache")
    public List<Comment> getCommentByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Comment s where s.user.id = :idusr";
        return session.createQuery(hql, Comment.class)
                .setParameter("idusr", idUser)
                .getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public List<Comment> getCommentByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Comment s where s.tvShow.id = :idtv";
        return session.createQuery(hql, Comment.class)
                .setParameter("idtv", idTvShow)
                .getResultList();
    }

    @Override
    @Cacheable("application-cache")
    public List<Comment> getComments(long idTvShow, long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Comment s where s.tvShow.id = :idtv and s.user.id = :idusr";
        return session.createQuery(hql, Comment.class)
                .setParameter("idtv", idTvShow)
                .setParameter("idusr", idUser)
                .getResultList();
    }
}
