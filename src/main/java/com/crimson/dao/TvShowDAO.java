package com.crimson.dao;

import com.crimson.model.TvShow;
import com.crimson.model.User;
import com.github.slugify.Slugify;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TvShowDAO {

    @Autowired
    private SessionFactory sf;

    public void saveTvShow(TvShow tv) {
        Session session = sf.getCurrentSession();
        Slugify slg = new Slugify();
        tv.setSlug(slg.slugify(tv.getTitle()));
        session.persist(tv);
    }

    public List<TvShow> getAllTvShows() {
        Session session = sf.getCurrentSession();
        List<TvShow> tv = session.createQuery("Select a From TvShow a", TvShow.class).getResultList();
        return tv;
    }

    public TvShow getTvById(Long id) {
        Session session = sf.getCurrentSession();
        return session.find(TvShow.class, id);
    }

    public TvShow getTvBySlug(String slug) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class).setParameter("custSlug", slug).getSingleResult();
    }

    public List<TvShow> getTvByGenre(String genre) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class).setParameter("custGenre", genre).getResultList();
    }

    public void deleteTvShow(TvShow tvshow){
        Session session = sf.getCurrentSession();
        session.delete(tvshow);
    }

    public void updateTvShow(TvShow tvshow){
        Session session = sf.getCurrentSession();
        session.update(tvshow);
    }

    public TvShow getTvShowBySlug(String slug) {
        Session session = sf.getCurrentSession();
        TvShow tv = session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class).setParameter("custSlug", slug).getSingleResult();
        return tv;
    }

    //RELATIONSHIPS

    //User2TvShow
    public void addUser2TvShows(User user, TvShow tvShow){
        if (!user.Users2TvShow.contains(tvShow))
        {
            user.Users2TvShow.add(tvShow);
        }
        if (!tvShow.TvShows2User.contains(user)){
            tvShow.TvShows2User.add(user);
        }
    }

    public void deleteUser2TvShow(User user, TvShow tvShow){
        if(user.Users2TvShow.contains(tvShow)){
            user.Users2TvShow.remove(tvShow);
        }
        if (tvShow.TvShows2User.contains(user)){
            tvShow.TvShows2User.remove(user);
        }
    }
}