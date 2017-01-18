package com.crimson.dao;

import com.crimson.model.Episode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class EpisodeDAO {

    @Autowired
    private SessionFactory sf;

    public void saveEpisode(Episode episode){
        Session session = sf.getCurrentSession();
        session.persist(episode);
    }

    public void deleteEpisode(Episode episode){
        Session session = sf.getCurrentSession();
        session.delete(episode);
    }

    public void updateEpisode(Episode episode){
        Session session = sf.getCurrentSession();
        session.update(episode);
    }

    public Episode getEpisodeById(Long idEpisode){
        Session session = sf.getCurrentSession();
        return session.find(Episode.class, idEpisode);
    }

    public List<Episode> getAllEpisodes(){
        Session session = sf.getCurrentSession();
        List<Episode> episodes = session.createQuery("SELECT a FROM Episode a", Episode.class).getResultList();
        return episodes;
    }

    public Episode getEpisodeByTitle(String title){
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From Episode a where a.title like :custTitle", Episode.class).setParameter("custTitle", title).getSingleResult();
    }
}