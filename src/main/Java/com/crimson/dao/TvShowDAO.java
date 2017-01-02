package com.crimson.dao;

import com.crimson.model.TvShow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TvShowDAO {

    @Autowired
    private SessionFactory sf;


    public void saveTvShow(TvShow tv) {
        Session session = sf.getCurrentSession();
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

    public void deleteTvShow(TvShow tvshow){
        Session session = sf.getCurrentSession();
        session.delete(tvshow);
    }

    public void updateTvShow(TvShow tvshow){
        Session session = sf.getCurrentSession();
        session.update(tvshow);
    }
}