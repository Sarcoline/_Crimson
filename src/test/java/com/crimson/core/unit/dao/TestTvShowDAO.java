package com.crimson.core.unit.dao;

import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.TvShowDAOImpl;
import com.crimson.core.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestTvShowDAO {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @InjectMocks
    private TvShowDAO tvShowDAO = new TvShowDAOImpl();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() throws Exception{
        //given
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).persist(tvShow);

        //when
        tvShowDAO.save(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).persist(tvShow);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From TvShow a", TvShow.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.getAll();

        //then
        verify(sessionFactory,times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From TvShow a", TvShow.class);
        verify(query, times(1)).getResultList();
        Assert.assertEquals(tvShows.equals(gettedList), true);
    }

    @Test
    @SuppressWarnings("unused")
    public void getByIdTest() throws Exception{
        //given
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.find(TvShow.class, tvShow.getId())).thenReturn(tvShow);

        //when
        TvShow gettedTvShow = tvShowDAO.getById(tvShow.getId());


        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).find(TvShow.class, tvShow.getId());
    }

    @Test
    @SuppressWarnings("unused")
    public void getTvByIdWithEpisodesTest() throws Exception{
        //given
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.find(TvShow.class, tvShow.getId())).thenReturn(tvShow);

        //when
        TvShow gettedTvShow = tvShowDAO.getTvByIdWithEpisodes(tvShow.getId());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).find(TvShow.class, tvShow.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTvBySlugTest() throws Exception{
        //given
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class)).thenReturn(query);
        when(query.setParameter("custSlug", tvShow.getSlug())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(tvShow);

        //when
        TvShow gettedTvShow = tvShowDAO.getTvBySlug(tvShow.getSlug());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From TvShow a where a.slug like :custSlug", TvShow.class);
        verify(query, times(1)).setParameter("custSlug", tvShow.getSlug());
        verify(query, times(1)).getSingleResult();
        Assert.assertEquals(tvShow.equals(gettedTvShow), true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTvByGenreTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class)).thenReturn(query);
        when(query.setParameter("custGenre", tvShow.getGenre())).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.getTvByGenre(tvShow.getGenre());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From TvShow a where a.genre like :custGenre", TvShow.class);
        verify(query, times(1)).setParameter("custGenre", tvShow.getGenre());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(tvShows.equals(gettedList), true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTvByCountryTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From TvShow a where a.country like :custCountry", TvShow.class)).thenReturn(query);
        when(query.setParameter("custCountry", tvShow.getCountry())).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.getTvByCountry(tvShow.getCountry());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From TvShow a where a.country like :custCountry", TvShow.class);
        verify(query, times(1)).setParameter("custCountry", tvShow.getCountry());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(tvShows.equals(gettedList), true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTvByYearTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From TvShow a where a.releaseYear = :custReleaseYear", TvShow.class)).thenReturn(query);
        when(query.setParameter("custReleaseYear", tvShow.getReleaseYear())).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.getTvByYear(tvShow.getReleaseYear());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From TvShow a where a.releaseYear = :custReleaseYear", TvShow.class);
        verify(query, times(1)).setParameter("custReleaseYear", tvShow.getReleaseYear());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(tvShows.equals(gettedList), true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTvByNetworkTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From TvShow a where a.network like :custNetwork", TvShow.class)).thenReturn(query);
        when(query.setParameter("custNetwork", tvShow.getNetwork())).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.getTvByNetwork(tvShow.getNetwork());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From TvShow a where a.network like :custNetwork", TvShow.class);
        verify(query, times(1)).setParameter("custNetwork", tvShow.getNetwork());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(tvShows.equals(gettedList), true);
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).delete(tvShow);

        //when
        tvShowDAO.delete(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).delete(tvShow);
    }

    //TODO Sprawdzić czy dobrze
    @Test
    public void updateTest() throws Exception{
        //given
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).saveOrUpdate(tvShow);

        //when
        tvShow.setTitle("New Title");
        tvShowDAO.update(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        //verify(session, times(1)).saveOrUpdate(tvShow);
    }

    @Test
    @SuppressWarnings("unused")
    public void searchTvShowTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        String pattern = "";
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM TvShow t WHERE title like :pattern";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("pattern", String.format("%%%s%%", pattern))).thenReturn(query);
        when(query.setMaxResults(5)).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.searchTvShow(pattern);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("pattern", String.format("%%%s%%", pattern));
        verify(query, times(1)).setMaxResults(5);
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void filterTvShowsTest() throws Exception{
        //given
        List<TvShow> tvShows = new ArrayList<>();
        Double min = 1.0, max = 10.0;
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "from TvShow t WHERE overallRating between :start and :finish";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("start", min)).thenReturn(query);
        when(query.setParameter("finish", max)).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.filterTvShows(min, max);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("start", min);
        verify(query, times(1)).setParameter("finish", max);
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void tvShowsSizeTest() throws Exception{
        //given
        Long couter = 0L;
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT count(x) FROM TvShow x")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(couter);

        //when
        Long gettedCounter = tvShowDAO.tvShowsSize();

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("SELECT count(x) FROM TvShow x");
        verify(query, times(1)).getSingleResult();
    }

    @Test
    @SuppressWarnings("unused")
    public void getTvShowsToPaginationByQueryTest() throws Exception{
        //given
        Long counter = 0L;
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT count (id) FROM TvShow  f")).thenReturn(query);
        when(query.uniqueResult()).thenReturn(counter);

        //when
        Long gettedCounter = tvShowDAO.getTvShowsToPaginationByQuery();

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("SELECT count (id) FROM TvShow  f");
        verify(query, times(1)).uniqueResult();
    }

    @Test
    @SuppressWarnings("unchecked, unused")
    public void queryGettingTvShowListForPageTest() throws Exception{
        //given
        int pageNumber = 0, maxResults = 25;
        List<TvShow> tvShows = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from TvShow ")).thenReturn(query);
        when(query.setFirstResult((pageNumber - 1) * 25)).thenReturn(query);
        when(query.setMaxResults(maxResults)).thenReturn(query);
        when(query.list()).thenReturn(tvShows);

        //when
        List<TvShow> gettedList = tvShowDAO.queryGettingTvShowListForPage(pageNumber, maxResults);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("from TvShow ");
        verify(query, times(1)).setFirstResult((pageNumber - 1) * 25);
        verify(query, times(1)).setMaxResults(maxResults);
        verify(query, times(1)).list();
    }
    @Test
    @SuppressWarnings("unused")
    public void getUsersTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM User u JOIN FETCH u.tvShows t where t.id = :id";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", tvShow.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<User> gettedList = tvShowDAO.getUsers(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("id", tvShow.getId());
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void getGenresTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM Genre g JOIN FETCH g.tvShows t where t.id = :id";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", tvShow.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<Genre> gettedList = tvShowDAO.getGenres(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("id", tvShow.getId());
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void getEpisodesTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM Episode e JOIN FETCH e.tvShow t where t.id = :id";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", tvShow.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<Episode> gettedList = tvShowDAO.getEpisodes(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("id", tvShow.getId());
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void getRatingsTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM Rating r JOIN FETCH r.tvShow t where t.id = :id";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", tvShow.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<Rating> gettedList = tvShowDAO.getRatings(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("id", tvShow.getId());
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void getCommentsTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM Comment c JOIN FETCH c.tvShow t where t.id = :id";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", tvShow.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<Comment> gettedList = tvShowDAO.getComments(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("id", tvShow.getId());
        verify(query, times(1)).getResultList();
    }

    @Test
    @SuppressWarnings("unused")
    public void getReviewsTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        TvShow tvShow = new TvShow();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        String hql = "FROM Review r JOIN FETCH r.tvShow t where t.id = :id";
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", tvShow.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<Review> gettedList = tvShowDAO.getReviews(tvShow);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(hql);
        verify(query, times(1)).setParameter("id", tvShow.getId());
        verify(query, times(1)).getResultList();
    }
}
