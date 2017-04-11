package com.crimson.core.dao;

import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
public class TvShowDAOImpl implements TvShowDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //Getting and returning session factory
    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    //Saving TvShow object to database and setting slug
    @Override
    public void save(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        Slugify slg = new Slugify();
        tv.setSlug(slg.slugify(tv.getTitle()));
        session.persist(tv);
    }

    //Getting TvShows objects from database and returning TvShows list
    @Override
    @Cacheable("application-cache")
    public List<TvShow> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a", TvShow.class).getResultList();
    }

    //Getting TvShow object from database by id
    @Override
    @Cacheable("application-cache")
    public TvShow getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(TvShow.class, id);
    }

    //Getting TvShow object from database with relational Episodes objects
    @Override
    @Cacheable("application-cache")
    public TvShow getTvByIdWithEpisodes(Long id) {
        Session session = sessionFactory.getCurrentSession();

        TvShow tvshow = session.find(TvShow.class, id);
        Hibernate.initialize(tvshow.getEpisodes());

        return tvshow;
    }

    //Getting TvShow object from database by slug
    @Override
    @Cacheable("application-cache")
    public TvShow getTvBySlug(String slug) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class)
                .setParameter("custSlug", slug).getSingleResult();
    }

    //Getting TvShow object from database by genre
    @Override
    @Cacheable("application-cache")
    public List<TvShow> getTvByGenre(String genre) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class)
                .setParameter("custGenre", genre).getResultList();

    }

    //Getting TvShow object from database by country
    @Override
    @Cacheable("application-cache")
    public List<TvShow> getTvByCountry(String country) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.country like :custCountry", TvShow.class)
                .setParameter("custCountry", country).getResultList();
    }

    //Getting TvShow object from database by year
    @Override
    @Cacheable("application-cache")
    public List<TvShow> getTvByYear(int releaseYear) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.releaseYear = :custReleaseYear", TvShow.class)
                .setParameter("custReleaseYear", releaseYear).getResultList();
    }

    //Getting TvShow object from database by network
    @Override
    @Cacheable("application-cache")
    public List<TvShow> getTvByNetwork(String network) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.network like :custNetwork", TvShow.class)
                .setParameter("custNetwork", network).getResultList();
    }


    //Deleting TvShow object from database
    @Override
    public void delete(TvShow tvshow) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tvshow);
    }

    //Updating TvShow object from database
    @Override
    public void update(TvShow tvshow) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tvshow);
    }

    //Method to search TvShow object from database by pattern
    @Override
    public List<TvShow> searchTvShow(String pattern) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TvShow t WHERE title like :pattern";
        return session.createQuery(hql, TvShow.class)
                .setParameter("pattern", String.format("%%%s%%", pattern))
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public List<Long> getIdsOfWatchedEpisodes(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select e.id FROM Episode e JOIN e.users u WHERE u.name = :name";
        return session.createQuery(hql, Long.class).setParameter("name", name).getResultList();
    }

    //Method to filter TvShows object by min and max attributes
    @Override
    public List<TvShow> filterTvShows(double min, double max) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from TvShow t WHERE overallRating between :start and :finish";
        return session.createQuery(hql, TvShow.class)
                .setParameter("start", min)
                .setParameter("finish", max)
                .getResultList();
    }


    //Method to return size of TvShows object in database
    @Override
    public long tvShowsSize() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT count(x) FROM TvShow x");
        return (long) q.getSingleResult();
    }

    //Method to count and return size of TvShows object in database
    @Override
    public Long getTvShowsToPaginationByQuery() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("SELECT count (id) FROM TvShow  f").uniqueResult();
    }

    //Method to return TvShows object list for requested page
    @Override
    public List<TvShow> queryGettingTvShowListForPage(int pageNumber, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from TvShow ", TvShow.class)
                .setFirstResult((pageNumber - 1) * 25)
                .setMaxResults(25)
                .getResultList();
    }

    //Getting relational Users objects from TvShow object from database
    @Override
    @Cacheable("application-cache")
    public List<User> getUsers(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.tvShows t where t.id = :id";
        return session.createQuery(hql, User.class)
                .setParameter("id", tv.getId())
                .getResultList();
    }

    //Getting relational Genres objects from TvShow object from database
    @Override
    @Cacheable("application-cache")
    public List<Genre> getGenres(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Genre g JOIN FETCH g.tvShows t where t.id = :id";
        return session.createQuery(hql, Genre.class)
                .setParameter("id", tv.getId())
                .getResultList();
    }

    //Getting relational Episodes objects from TvShow object from database
    @Override
    @Cacheable("application-cache")
    public List<Episode> getEpisodes(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Episode e JOIN FETCH e.tvShow t where t.id = :id";
        return session.createQuery(hql, Episode.class)
                .setParameter("id", tv.getId())
                .getResultList();
    }

    //Getting relational Ratings objects from TvShow object from database
    @Override
    @Cacheable("application-cache")
    public List<Rating> getRatings(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Rating r JOIN FETCH r.tvShow t where t.id = :id";
        return session.createQuery(hql, Rating.class)
                .setParameter("id", tv.getId())
                .getResultList();
    }

    //Getting relational Comments objects from TvShow object from database
    @Override
    @Cacheable("application-cache")
    public List<Comment> getComments(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Comment c JOIN FETCH c.tvShow t where t.id = :id";
        return session.createQuery(hql, Comment.class)
                .setParameter("id", tv.getId())
                .getResultList();
    }

    //Getting relational Reviews objects from TvShow object from database
    @Override
    @Cacheable("application-cache")
    public List<Review> getReviews(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Review r JOIN FETCH r.tvShow t where t.id = :id";
        return session.createQuery(hql, Review.class)
                .setParameter("id", tv.getId())
                .getResultList();
    }

    //Getting TvShow picture from database by slug
    @Override
    @Cacheable("application-cache")
    @SuppressWarnings("unchecked")
    public HashMap<String, byte[]> getTvShowPicture(String slug) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select u.pictures from TvShow u where u.slug = :custName";
        return (HashMap<String, byte[]>) session.createQuery(hql).setParameter("custName", slug).getSingleResult();
    }

    //Getting TvShows object list with max rating from database
    @Override
    @Cacheable("application-cache")
    public List<TvShow> getTvShowsByMaxRating() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from TvShow t order by t.overallRating desc ";
        return session.createQuery(hql, TvShow.class).setMaxResults(10)
                .getResultList();
    }
}