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

    @Override
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        Slugify slg = new Slugify();
        tv.setSlug(slg.slugify(tv.getTitle()));
        session.persist(tv);
    }

    @Override
    @Cacheable("myCache")
    public List<TvShow> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a", TvShow.class).getResultList();
    }

    @Override
    @Cacheable("myCache")
    public TvShow getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(TvShow.class, id);
    }

    @Override
    @Cacheable("myCache")
    public TvShow getTvByIdWithEpisodes(Long id) {
        Session session = sessionFactory.getCurrentSession();

        TvShow tvshow = session.find(TvShow.class, id);
        Hibernate.initialize(tvshow.getEpisodes());

        return tvshow;
    }

    @Override
    @Cacheable("myCache")
    public TvShow getTvBySlug(String slug) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class)
                .setParameter("custSlug", slug).getSingleResult();
    }

    @Override
    @Cacheable("myCache")
    public List<TvShow> getTvByGenre(String genre) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class)
                .setParameter("custGenre", genre).getResultList();

    }

    @Override
    @Cacheable("myCache")
    public List<TvShow> getTvByCountry(String country) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.country like :custCountry", TvShow.class)
                .setParameter("custCountry", country).getResultList();
    }

    @Override
    @Cacheable("myCache")
    public List<TvShow> getTvByYear(int releaseYear) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.releaseYear = :custReleaseYear", TvShow.class)
                .setParameter("custReleaseYear", releaseYear).getResultList();
    }

    @Override
    @Cacheable("myCache")
    public List<TvShow> getTvByNetwork(String network) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.network like :custNetwork", TvShow.class)
                .setParameter("custNetwork", network).getResultList();
    }


    @Override
    public void delete(TvShow tvshow) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tvshow);
    }

    @Override
    public void update(TvShow tvshow) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tvshow);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TvShow> searchTvShow(String pattern) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TvShow t WHERE title like :pattern";
        Query query = session.createQuery(hql);
        query.setParameter("pattern", String.format("%%%s%%", pattern));
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TvShow> filterTvShows(double min, double max) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from TvShow t WHERE overallRating between :start and :finish";
        Query query = session.createQuery(hql);
        query.setParameter("start", min);
        query.setParameter("finish", max);
        return query.getResultList();
    }


    @Override
    public long tvShowsSize() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT count(x) FROM TvShow x");
        return (long) q.getSingleResult();
    }


    //Zwraca ilość seriali w bazie do obliczenia ostatniej strony
    @Override
    public Long getTvShowsToPaginationByQuery()
    {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("SELECT count (id) FROM TvShow  f").uniqueResult();
    }

    //Zwraca listę seriali dla podanej strony
    @Override
    public List queryGettingTvShowListForPage(int pageNumber, int maxResults){
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.query.Query selectQuery = session.createQuery("from TvShow ");
        selectQuery.setFirstResult((pageNumber - 1) * 25);
        selectQuery.setMaxResults(25);
        return selectQuery.list();
    }

    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getUsers().add(user);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getUsers().remove(user);
        session.saveOrUpdate(tvShow);
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getGenres().add(genre);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getGenres().remove(genre);
        session.saveOrUpdate(tvShow);
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getEpisodes().add(episode);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getEpisodes().remove(episode);
        session.saveOrUpdate(tvShow);
    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getRatings().add(rating);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getRatings().remove(rating);
        session.saveOrUpdate(tvShow);
    }

    //TvShow2Comment

    @Override
    public void addComment(TvShow tvShow, Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getComments().add(comment);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void addReview(TvShow tvShow, Review review) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getReviews().add(review);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteComment(TvShow tvShow, Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getComments().remove(comment);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteReview(TvShow tvShow, Review review) {
        Session session = sessionFactory.getCurrentSession();
        tvShow.getReviews().remove(review);
        session.saveOrUpdate(tvShow);
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<User> getUsers(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.tvShows t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<Genre> getGenres(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Genre g JOIN FETCH g.tvShows t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<Episode> getEpisodes(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Episode e JOIN FETCH e.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<Rating> getRatings(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Rating r JOIN FETCH r.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<Comment> getComments(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Comment c JOIN FETCH c.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<Review> getReviews(TvShow tv) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Review r JOIN FETCH r.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @Override
    @Cacheable("myCache")
    public HashMap<String, byte[]> getTvShowPicture(String slug) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select u.pictures from TvShow u where u.slug = :custName";
        HashMap<String, byte[]> pic = (HashMap<String, byte[]>) session.createQuery(hql).setParameter("custName", slug).getSingleResult();
        return pic;
    }

    @Override
    @Cacheable("myCache")
    @SuppressWarnings("unchecked")
    public List<TvShow> getTvShowsByMaxRating() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from TvShow t order by t.overallRating desc ";
        return session.createQuery(hql).setMaxResults(10)
                .getResultList();
    }
}