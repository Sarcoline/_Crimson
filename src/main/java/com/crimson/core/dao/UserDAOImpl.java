package com.crimson.core.dao;

import com.crimson.core.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From User a", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(User.class, id);
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public User getUserByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From User a where a.name like :custName", User.class)
                .setParameter("custName", name).getSingleResult();
    }
    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addTvShow2User(User user, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        user.getTvShows().add(tvShow);
        session.saveOrUpdate(user);

    }

    @Override
    public void deleteTvShowFromUser(User user, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        user.getTvShows().remove(tvShow);
        session.saveOrUpdate(user);
    }

    //User2Episode
    @Override
    public void addEpisode2User(User user, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        user.getEpisodes().add(episode);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteEpisodeFromUser(User user, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        user.getEpisodes().remove(episode);
        session.saveOrUpdate(user);
    }

    //Rating

    @Override
    public void addRating2User(User user, Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        user.getRatings().add(rating);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteRatingFromUser(User user, Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        user.getRatings().remove(rating);
        session.saveOrUpdate(user);
    }

    //User2Setting

    @Override
    public void addSetting2User(User user, Setting setting) {
        Session session = sessionFactory.getCurrentSession();
        user.setSetting(setting);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteSettingFromUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        user.setSetting(null);
        session.saveOrUpdate(user);
    }

    //User2Role

    @Override
    public void addRole2User(User user, Role role) {
        Session session = sessionFactory.getCurrentSession();
        user.getRoles().add(role);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteRoleFromUser(User user, Role role) {
        Session session = sessionFactory.getCurrentSession();
        user.getRoles().remove(role);
        session.saveOrUpdate(user);
    }

    //User2Comment

    @Override
    public void addComment(User user, Comment comment){
        Session session = sessionFactory.getCurrentSession();
        user.getComments().add(comment);
        session.saveOrUpdate(user);
    }

    @Override
    public void addReview(User user, Review review){
        Session session = sessionFactory.getCurrentSession();
        user.getReviews().add(review);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteComment(User user, Comment comment){
        Session session = sessionFactory.getCurrentSession();
        user.getComments().remove(comment);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteReview(User user, Review review){
        Session session = sessionFactory.getCurrentSession();
        user.getReviews().remove(review);
        session.saveOrUpdate(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TvShow> getTvShows(User user){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TvShow t JOIN FETCH t.users u where u.id = ?";
        return session.createQuery(hql)
                .setParameter(0, user.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Episode> getEpisodes(User user){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Episode e JOIN FETCH e.users u where u.id = ?";
        return session.createQuery(hql)
                .setParameter(0, user.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Rating> getRatings(User user){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Rating r JOIN FETCH r.user u where u.id = ?";
        return session.createQuery(hql)
                .setParameter(0, user.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getRoles(User user){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Role r JOIN FETCH r.users u where u.id = ?";
        return session.createQuery(hql)
                .setParameter(0, user.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Review> getReviews(User user){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Review r JOIN FETCH r.user u where u.id = ?";
        return session.createQuery(hql)
                .setParameter(0, user.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> getComments(User user){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Comment c JOIN FETCH c.user u where u.id = ?";
        return session.createQuery(hql)
                .setParameter(0, user.getId())
                .getResultList();
    }
}