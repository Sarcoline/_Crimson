package com.crimson.core.dao;

import com.crimson.core.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //Saving user object to database
    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    //Getting all user object from database and set to list
    @Override
    @Cacheable("application-cache")
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From User a", User.class).getResultList();
    }

    //Getting choosed by id user object from database
    @Override
    public User getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(User.class, id);
    }

    //Deleting user object from database
    @Override
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    //Updating user object in database
    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    //Getting user object from database by username
    @Override
    @Cacheable("application-cache")
    public User getUserByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From User a where a.name like :custName", User.class)
                .setParameter("custName", name).getSingleResult();
    }

    //Getting user object from database by token
    @Override
    @Cacheable("application-cache")
    public User getUserByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        User user;
        try {
            user = session.createQuery("Select a From User a where a.token like :custToken", User.class)
                    .setParameter("custToken", token).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    //Getting relational TvShows objects from User object from database
    @Override
    public List<TvShow> getTvShows(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TvShow t JOIN FETCH t.users u where u.id = :id";
        return session.createQuery(hql, TvShow.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    //Getting relational Episodes objects from User object from database
    @Override
    public List<Episode> getEpisodes(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Episode e JOIN FETCH e.users u where u.id = :id";
        return session.createQuery(hql, Episode.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    //Getting relational Ratings objects from User object from database
    @Override
    public List<Rating> getRatings(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Rating r JOIN FETCH r.user u where u.id = :id";
        return session.createQuery(hql, Rating.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    //Getting relational Roles objects from User object from database
    @Override
    public List<Role> getRoles(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Role r JOIN FETCH r.users u where u.id = :id";
        return session.createQuery(hql, Role.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    //Getting relational Reviews objects from User object from database
    @Override
    public List<Review> getReviews(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Review r JOIN FETCH r.user u where u.id = :id";
        return session.createQuery(hql, Review.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    //Getting user object from database by email
    @Override
    @Cacheable("application-cache")
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        User user;
        try {
            user = session.createQuery("Select a From User a where a.email like :custEmail", User.class)
                    .setParameter("custEmail", email).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    //Getting User profile picture by username
    @Override
    @Cacheable("application-cache")
    public byte[] getUserProfilePicture(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select u.profilePic from User u where u.name = :custName";
        return (byte[]) session.createQuery(hql).setParameter("custName", name).getSingleResult();
    }

    @Override
    public boolean checkFollow(String userName, long idTv) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.tvShows t where u.name = :name and t.id = :id";
        List tvs =  session.createQuery(hql)
                .setParameter("name", userName)
                .setParameter("id", idTv)
                .getResultList();
        return !tvs.isEmpty();
    }

    @Override
    public boolean checkWatched(String userName, long idEpisode) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.episodes e where u.name = :name and e.id = :id";
        List tvs =  session.createQuery(hql)
                .setParameter("name", userName)
                .setParameter("id", idEpisode)
                .getResultList();
        return !tvs.isEmpty();
    }

    //Getting relational Comments objects from User object from database
    @Override
    @Cacheable("application-cache")
    public List<Comment> getComments(User user) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Comment c JOIN FETCH c.user u where u.id = :id";
        return session.createQuery(hql, Comment.class)
                .setParameter("id", user.getId())
                .getResultList();
    }
}