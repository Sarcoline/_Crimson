package com.crimson.core.dao;

import com.crimson.core.model.*;
import com.github.slugify.Slugify;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class TvShowDAOImpl implements TvShowDAO {

    @Autowired
    private SessionFactory sf;

    @Override
    public void saveTvShow(TvShow tv) {
        Session session = sf.getCurrentSession();
        Slugify slg = new Slugify();
        tv.setSlug(slg.slugify(tv.getTitle()));
        session.persist(tv);
    }

    @Override
    public List<TvShow> getAllTvShows() {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a", TvShow.class).getResultList();
    }

    @Override
    public TvShow getTvById(Long id) {
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
        return session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class).setParameter("custSlug", slug).getSingleResult();
    }

    @Override
    public List<TvShow> getTvByGenre(String genre) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class).setParameter("custGenre", genre).getResultList();

    }

    @Override
    public List<TvShow> getTvByCountry(String country) {
        Session session = sf.getCurrentSession();
        return session.createQuery("Select a From TvShow a where a.country like :custCountry", TvShow.class).setParameter("custCountry", country).getResultList();
    }



    @Override
    public void deleteTvShow(TvShow tvshow) {
        Session session = sf.getCurrentSession();
        session.delete(tvshow);
    }

    @Override
    public void updateTvShow(TvShow tvshow) {
        Session session = sf.getCurrentSession();
        session.update(tvshow);
    }


    //Extra Methods

    @Override
    public List<TvShow> getAllTvShowByMaxRating() {
        List<TvShow> unsortedList = getAllTvShows();
        unsortedList.sort(Comparator.comparing(TvShow::getOverallRating));
        Collections.reverse(unsortedList);
        return unsortedList;
    }

    @Override
    public List<TvShow> searchTvShow(String pattern) {
        Session session = sf.getCurrentSession();
        String hql = "FROM TvShow t WHERE title like :pattern";
        Query query = session.createQuery(hql);
        query.setParameter("pattern", String.format("%%%s%%", pattern));
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public List<TvShow> filterTvShows(double min, double max) {
        Session session = sf.getCurrentSession();
        String hql = "from TvShow t WHERE overallRating between :start and :finish";
        Query query = session.createQuery(hql);
        query.setParameter("start", min);
        query.setParameter("finish", max);
        return query.getResultList();
    }



    //Wyrzuca listę TvShow dzieląc cała listę na podany przedział przez użytkownika na stronie np. ma wyświetlić tylko 10 tvShows na jednej podstronie
    @Override
    public List<TvShow> getTvShowsSortedByNumberOnList(int userChoosedNumberOnList, int pageNumber) {
        List<TvShow> allTvShows = getAllTvShows();
        List<TvShow> listToReturn = new ArrayList<>();

        try {
            int counter, sizeOfAllTvShows = getAllTvShows().size();
            //Od jakiego tvshow startuje
            int startFrom = userChoosedNumberOnList * (pageNumber - 1);
            //Sprawdza czy na żądanej stronie baza nie posiada za mało Tvshows do wyświetlenia
            if (sizeOfAllTvShows - (userChoosedNumberOnList * pageNumber) <= 0) {
                //jesli tak to oblicza ilosc tvshow ktorych brakuje
                int tmp = sizeOfAllTvShows - (userChoosedNumberOnList * pageNumber);
                //ustawia licznik na ilosc TvShow do zwrócenia na danej
                counter = userChoosedNumberOnList + tmp - 1;
            } else {
                counter = userChoosedNumberOnList - 1;
            }

            while (counter >= 0) {
                listToReturn.add(allTvShows.get(startFrom + counter));
                counter--;
            }

            Collections.reverse(listToReturn);
            return listToReturn;
        } catch (Exception e) {
            return null;
        }
    }


//    public List<TvShow> getTvShowsSortedByUserNumberToShow(List<TvShow> allTvShows, int userChoosedNumberOnList, int pageNumber){
//
//    }

    //RELATIONSHIPS

    //User2TvShow

    @Override
    public void addUser2TvShow(User user, TvShow tvShow) {
        if (!tvShow.getTvShowUserList().contains(user)) {
            tvShow.getTvShowUserList().add(user);
        }
    }

    @Override
    public void deleteUserFromTvShow(User user, TvShow tvShow) {
        if (tvShow.getTvShowUserList().contains(user)) {
            tvShow.getTvShowUserList().remove(user);
        }
    }

    //TvShow2Genre

    @Override
    public void addGenre2TvShow(TvShow tvShow, Genre genre) {
        if (!tvShow.getTvShowGenreList().contains(genre)) {
            tvShow.getTvShowGenreList().add(genre);
        }
    }

    @Override
    public void deleteGenreFromTvShow(TvShow tvShow, Genre genre) {
        if (tvShow.getTvShowGenreList().contains(genre)) {
            tvShow.getTvShowGenreList().remove(genre);
        }
    }

    //TvShow2Episode

    @Override
    public void addEpisode2TvShow(TvShow tvShow, Episode episode) {
        if (!tvShow.getEpisodes().contains(episode)) {
            tvShow.getEpisodes().add(episode);
        }
    }

    @Override
    public void deleteEpisodeFromTvShow(TvShow tvShow, Episode episode) {
        if (tvShow.getEpisodes().contains(episode)) {
            tvShow.getEpisodes().remove(episode);
        }
    }

    //TvShowRating

    @Override
    public void addRating2TvShow(TvShow tvShow, Rating rating) {
        if (!tvShow.getTvShowRating().contains(rating)) {
            tvShow.getTvShowRating().add(rating);
        }
    }

    @Override
    public void deleteRatingFromTvShow(TvShow tvShow, Rating rating) {
        if (tvShow.getTvShowRating().contains(rating)) {
            tvShow.getTvShowRating().remove(rating);
        }
    }


}