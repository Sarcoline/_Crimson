package com.crimson.core.dao;

import com.crimson.core.dto.FilterResponse;
import com.crimson.core.dto.SearchFilterParameters;
import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TvShowDAOImpl implements TvShowDAO {

    @Autowired
    private SessionFactory sf;

    @Override
    public void save(TvShow tv) {
        Session session = sf.getCurrentSession();
        Slugify slg = new Slugify();
        tv.setSlug(slg.slugify(tv.getTitle()));
        session.persist(tv);
    }

    @Override
    public List<TvShow> getAll() {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a", TvShow.class).getResultList();
    }

    @Override
    public TvShow getById(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(TvShow.class, id);
    }

    @Override
    public TvShow getTvByIdWithEpisodes(Long id) {
        Session session = sf.getCurrentSession();

        TvShow tvshow = session.find(TvShow.class, id);
        Hibernate.initialize(tvshow.getEpisodes());

        return tvshow;
    }

    @Override
    public TvShow getTvBySlug(String slug) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class)
                .setParameter("custSlug", slug).getSingleResult();
    }

    @Override
    public List<TvShow> getTvByGenre(String genre) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class)
                .setParameter("custGenre", genre).getResultList();

    }

    @Override
    public List<TvShow> getTvByCountry(String country) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.country like :custCountry", TvShow.class)
                .setParameter("custCountry", country).getResultList();
    }

    @Override
    public List<TvShow> getTvByYear(int releaseYear) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.releaseYear = :custReleaseYear", TvShow.class)
                .setParameter("custReleaseYear", releaseYear).getResultList();
    }

    @Override
    public List<TvShow> getTvByNetwork(String network) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.network like :custNetwork", TvShow.class)
                .setParameter("custNetwork", network).getResultList();
    }


    @Override
    public void delete(TvShow tvshow) {
        Session session = sf.getCurrentSession();
        session.delete(tvshow);
    }

    @Override
    public void update(TvShow tvshow) {
        Session session = sf.getCurrentSession();
        session.update(tvshow);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TvShow> searchTvShow(String pattern) {
        Session session = sf.getCurrentSession();
        String hql = "FROM TvShow t WHERE title like :pattern";
        Query query = session.createQuery(hql);
        query.setParameter("pattern", String.format("%%%s%%", pattern));
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TvShow> filterTvShows(double min, double max) {
        Session session = sf.getCurrentSession();
        String hql = "from TvShow t WHERE overallRating between :start and :finish";
        Query query = session.createQuery(hql);
        query.setParameter("start", min);
        query.setParameter("finish", max);
        return query.getResultList();
    }


    @Override
    public long tvShowsSize() {
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("SELECT count(x) FROM TvShow x");
        return (long) q.getSingleResult();
    }

    //Wyrzuca ostatnia stronę przy pagination dla TvShow
    @Override
    public int tvShowsLastPageNumber() {
        Session session = sf.getCurrentSession();
        int listSizeOnPage = 25;
        Long countResults = (Long) session.createQuery("SELECT count (id) FROM TvShow  f").uniqueResult();
        if((countResults % listSizeOnPage) == 0) return (int) (countResults / listSizeOnPage);
        else return (int) (countResults / listSizeOnPage) + 1;
    }

    //Wyrzuca listę tvShow dla danej strony
    @Override
    @SuppressWarnings("unchecked")
    public List<TvShow> tvShowsPaginationList(int pageNumber) {
        Session session = sf.getCurrentSession();
        int lastPage = tvShowsLastPageNumber();
        List<TvShow> selectedList = new ArrayList<>();
        org.hibernate.query.Query selectQuery = session.createQuery("from TvShow ");

        if (pageNumber <= lastPage) {
            selectQuery.setFirstResult((pageNumber - 1) * 25);
            selectQuery.setMaxResults(25);
            selectedList = selectQuery.list();
        }
        return selectedList;
    }
    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow) {
        Session session = sf.getCurrentSession();
        if (!tvShow.getUsers().contains(user)) {
            tvShow.getUsers().add(user);
        }
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow) {
        Session session = sf.getCurrentSession();
        tvShow.getUsers().remove(user);
        session.saveOrUpdate(tvShow);
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre) {
        Session session = sf.getCurrentSession();
        if (!tvShow.getGenres().contains(genre)) {
            tvShow.getGenres().add(genre);
        }
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre) {
        Session session = sf.getCurrentSession();
        tvShow.getGenres().remove(genre);
        session.saveOrUpdate(tvShow);
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode) {
        Session session = sf.getCurrentSession();
        if (!tvShow.getEpisodes().contains(episode)) {
            tvShow.getEpisodes().add(episode);
        }
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode) {
        Session session = sf.getCurrentSession();
        tvShow.getEpisodes().remove(episode);
        session.saveOrUpdate(tvShow);
    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating) {
        Session session = sf.getCurrentSession();
        if (!tvShow.getRatings().contains(rating)) {
            tvShow.getRatings().add(rating);
        }
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating) {
        Session session = sf.getCurrentSession();
        tvShow.getRatings().remove(rating);
        session.saveOrUpdate(tvShow);
    }

    //TvShow2Comment

    @Override
    public void addComment(TvShow tvShow, Comment comment){
        Session session = sf.getCurrentSession();
        if (!tvShow.getComments().contains(comment)){
            tvShow.getComments().add(comment);
        }
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void addReview(TvShow tvShow, Review review){
        Session session = sf.getCurrentSession();
        if (!tvShow.getReviews().contains(review)){
            tvShow.getReviews().add(review);
        }
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteComment(TvShow tvShow, Comment comment){
        Session session = sf.getCurrentSession();
        tvShow.getComments().remove(comment);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public void deleteReview(TvShow tvShow, Review review){
        Session session = sf.getCurrentSession();
        tvShow.getReviews().remove(review);
        session.saveOrUpdate(tvShow);
    }

    @Override
    public FilterResponse filter(SearchFilterParameters parameters, int page) {
        FilterResponse response = new FilterResponse();
        Session session = sf.getCurrentSession();
        Criteria c = session.createCriteria(TvShow.class);
        if (parameters.getGenre() != null) {
            c.add(Restrictions.eq("genre", parameters.getGenre()));
        }
        if (parameters.getReleaseYearStart() != null) {
            c.add(Restrictions.ge("releaseYear", parameters.getReleaseYearStart()));
        }
        if (parameters.getReleaseYearEnd() != null) {
            c.add(Restrictions.le("releaseYear", parameters.getReleaseYearEnd()));
        }
        if (parameters.getCountry() != null) {
            c.add(Restrictions.eq("country", parameters.getCountry()));
        }
        if (parameters.getNetwork() != null) {
            c.add(Restrictions.eq("network", parameters.getNetwork()));
        }
        if (parameters.getMinimalRating() != null) {
            c.add(Restrictions.ge("overallRating", parameters.getMinimalRating()));
        }
        if (parameters.getMaximumRating() != null) {
            c.add(Restrictions.le("overallRating", parameters.getMaximumRating()));
        }
        int lastPage;
        int listSizeOnPage = 20;
        int countResults = (c.list().size());
        response.setSize(countResults);
        if((countResults % listSizeOnPage) == 0) lastPage = (countResults / listSizeOnPage);
        else lastPage = countResults / listSizeOnPage + 1;

        if (page <= lastPage) {
            c.setFirstResult((page - 1) * 20);
            c.setMaxResults(20);
        }
        response.setTvShows(c.list());
        return response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(TvShow tv){
        Session session = sf.getCurrentSession();
        String hql = "FROM User u JOIN FETCH u.tvShows t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Genre> getGenres(TvShow tv){
        Session session = sf.getCurrentSession();
        String hql = "FROM Genre g JOIN FETCH g.tvShows t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Episode> getEpisodes(TvShow tv){
        Session session = sf.getCurrentSession();
        String hql = "FROM Episode e JOIN FETCH e.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Rating> getRatings(TvShow tv){
        Session session = sf.getCurrentSession();
        String hql = "FROM Rating r JOIN FETCH r.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> getComments(TvShow tv){
        Session session = sf.getCurrentSession();
        String hql = "FROM Comment c JOIN FETCH c.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Review> getReviews(TvShow tv){
        Session session = sf.getCurrentSession();
        String hql = "FROM Review r JOIN FETCH r.tvShow t where t.id = ?";
        return session.createQuery(hql)
                .setParameter(0, tv.getId())
                .getResultList();
    }
}