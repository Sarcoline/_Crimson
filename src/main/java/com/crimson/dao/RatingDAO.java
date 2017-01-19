package com.crimson.dao;

import com.crimson.model.Rating;
import com.crimson.model.TvShow;
import com.crimson.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Repository
@Transactional
public class RatingDAO {

    @Autowired
    private SessionFactory sf;

    public void saveRating(Rating rating){
        Session session = sf.getCurrentSession();
        session.persist(rating);
    }

    public void deleteRating(Rating rating){
        Session session = sf.getCurrentSession();
        session.delete(rating);
    }

    public void updateRating(Rating rating){
        Session session = sf.getCurrentSession();
        session.update(rating);
    }
/*
    public Rating getRatingByIdUser(Long idUser){
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, idUser);
    }*/

    public Rating getRatingByID(Long id){
        Session session = sf.getCurrentSession();
        return session.find(Rating.class,id);
    }
/*
    public Rating getRatingByIdTvShow(Long idTvShow){
        Session session = sf.getCurrentSession();
        return session.find(Rating.class, idTvShow);
    } */

    public List<Rating> getAllRatings(){
        Session session = sf.getCurrentSession();
        List<Rating> ratings = session.createQuery("SELECT a FROM  Rating a", Rating.class).getResultList();
        return ratings;
    }


}
