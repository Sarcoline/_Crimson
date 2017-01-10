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
    @Autowired
    UserDAO userDAO;


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

    public TvShow getTvShowBySlug(String slug) {
        Session session = sf.getCurrentSession();
        TvShow tv = session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class).setParameter("custSlug", slug).getSingleResult();
        return tv;
    }

    public void deleteTvShow(TvShow tvshow){
        Session session = sf.getCurrentSession();
        for(User user : userDAO.getAllUsers()) {
            if(tvshow.getUsers().indexOf(user) > -1)
            userDAO.deleteUser2TvShow(user, tvshow);
        }
        session.delete(tvshow);
    }

    public void updateTvShow(TvShow tvshow){
        Session session = sf.getCurrentSession();
        session.update(tvshow);
    }

    public void addUser2TvShow(User user, TvShow tvshow){
        if(user.getTvShows().indexOf(tvshow) == -1)
            user.getTvShows().add(tvshow);
        if(tvshow.getUsers().indexOf(user)== -1)
            tvshow.getUsers().add(user);
    }

    public void deleteUser2TvShow(User user,TvShow tvshow){
        if(user.getTvShows().indexOf(tvshow) > -1)
            user.getTvShows().remove(tvshow);
        if(tvshow.getUsers().indexOf(user) > -1)
            tvshow.getUsers().remove(user);
    }
}