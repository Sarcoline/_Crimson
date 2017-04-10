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

    //Saving Comment object to database
    @Override
    public void save(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(comment);
    }

    //Deleting Comment object from database
    @Override
    public void delete(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(comment);
    }

    //Updating Comment object from database
    @Override
    public void update(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(comment);
    }

    //Getting Comment object from database by id
    @Override
    @Cacheable("application-cache")
    public Comment getById(Long idComment) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Comment.class, idComment);
    }

    //Getting Comments objects from database and returning list
    @Override
    @Cacheable("application-cache")
    public List<Comment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT a FROM Comment a", Comment.class).getResultList();
    }

    //Getting Comment object from database by date
    @Override
    @Cacheable("application-cache")
    public List<Comment> getCommentsByDate(LocalDate date) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From Comment a where a.date like :custDate", Comment.class).setParameter("custDate", date).getResultList();
    }

    //Getting Comment object from database by User id
    @Override
    @Cacheable("application-cache")
    public List<Comment> getCommentByIdUser(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Comment s where s.user.id = :idusr";
        return session.createQuery(hql, Comment.class)
                .setParameter("idusr", idUser)
                .getResultList();
    }

    //Getting Comment object from database by TvShow id
    @Override
    @Cacheable("application-cache")
    public List<Comment> getCommentByIdTvShow(Long idTvShow) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Comment s where s.tvShow.id = :idtv";
        return session.createQuery(hql, Comment.class)
                .setParameter("idtv", idTvShow)
                .getResultList();
    }

    //Getting relational Comments objects from database by TvShow id and User id
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
