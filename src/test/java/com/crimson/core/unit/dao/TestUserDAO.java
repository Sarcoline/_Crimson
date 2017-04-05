package com.crimson.core.unit.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dao.UserDAOImpl;
import com.crimson.core.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback
@RunWith(MockitoJUnitRunner.class)
public class TestUserDAO {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Query query;

    @InjectMocks
    private UserDAO userDAO = new UserDAOImpl();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).persist(anyObject());

        //when
        userDAO.save(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).persist(anyObject());
    }

    //TODO dowiedzieć się czy to jest poprawne
    @Test
    @SuppressWarnings("unchecked")
    public void getAllTest() throws Exception{
        //given
        List<User> users = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From User a",User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        //when
        List<User> getUsers = userDAO.getAll();

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From User a",User.class);
        verify(query, times(1)).getResultList();
        Assert.assertEquals(getUsers.size(), 0);
    }

    @Test
    public void getUserByIdTest() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.find(User.class, user.getId())).thenReturn(user);

        //when
        User getUser = userDAO.getById(user.getId());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).find(User.class, user.getId());
        Assert.assertEquals(user.equals(getUser), true);
    }

    @Test
    public void deleteUserTest() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).delete(user);

        //when
        userDAO.delete(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).delete(user);
    }

    @Test
    public void updateTest() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).saveOrUpdate(user);

        //when
        user.setName("New name");
        userDAO.update(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).saveOrUpdate(user);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserByName() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From User a where a.name like :custName", User.class)).thenReturn(query);
        when(query.setParameter("custName", user.getName())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        //when
        User getUser = userDAO.getUserByName(user.getName());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From User a where a.name like :custName", User.class);
        verify(query, times(1)).setParameter("custName", user.getName());
        verify(query, times(1)).getSingleResult();
        Assert.assertEquals(user.equals(getUser), true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserByToken() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From User a where a.token like :custToken", User.class)).thenReturn(query);
        when(query.setParameter("custToken", user.getToken())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        //when
        User getUser = userDAO.getUserByToken(user.getToken());

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From User a where a.token like :custToken", User.class);
        verify(query, times(1)).setParameter("custToken", user.getToken());
        verify(query, times(1)).getSingleResult();
        Assert.assertEquals(user.equals(getUser), true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserByEmailTest() throws Exception{
        //given
        User user = new User();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("Select a From User a where a.email like :custEmail", User.class)).thenReturn(query);
        when(query.setParameter("custEmail", user.getEmail())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        //when
        User gettedUser = userDAO.getUserByEmail(user.getEmail());

        //then

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery("Select a From User a where a.email like :custEmail", User.class);
        verify(query, times(1)).setParameter("custEmail", user.getEmail());
        verify(query, times(1)).getSingleResult();
        Assert.assertEquals(gettedUser.equals(user), true);
    }

    //RELATIONSHIPS GETS

    @Test
    public void getTvShowsTest() throws Exception{
        //given
        User user = new User();
        List<TvShow> tvShows = new ArrayList<>();
        String hql = "FROM TvShow t JOIN FETCH t.users u where u.id = :id";
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", user.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(tvShows);

        //when
        List<TvShow> getTvShows = userDAO.getTvShows(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter(anyString(), anyLong());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(getTvShows.equals(tvShows), true);
    }

    @Test
    public void getRatingsTest() throws Exception{
        //given
        User user = new User();
        List<Rating> ratings = new ArrayList<>();
        String hql = "FROM Rating r JOIN FETCH r.user u where u.id = :id";
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", user.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(ratings);

        //when
        List<Rating> getRatings = userDAO.getRatings(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter(anyString(), anyLong());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(getRatings.equals(ratings), true);
    }

    @Test
    public void getRolesTest() throws Exception{
        //given
        User user = new User();
        List<Role> roles = new ArrayList<>();
        String hql = "FROM Role r JOIN FETCH r.users u where u.id = :id";
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", user.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(roles);

        //when
        List<Role> gettedRoles = userDAO.getRoles(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter(anyString(), anyLong());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(gettedRoles.equals(roles), true);
    }

    @Test
    public void getReviewsTest() throws Exception{
        //given
        User user = new User();
        List<Review> reviews = new ArrayList<>();
        String hql = "FROM Review r JOIN FETCH r.user u where u.id = :id";
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", user.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(reviews);

        //when
        List<Review> gettedReviews = userDAO.getReviews(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter(anyString(), anyLong());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(gettedReviews.equals(reviews), true);
    }


    @Test
    public void getCommentsTest() throws Exception{
        //given
        User user = new User();
        List<Comment> comments = new ArrayList<>();
        String hql = "FROM Comment c JOIN FETCH c.user u where u.id = :id";
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql)).thenReturn(query);
        when(query.setParameter("id", user.getId())).thenReturn(query);
        when(query.getResultList()).thenReturn(comments);

        //when
        List<Comment> gettedComments = userDAO.getComments(user);

        //then
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter(anyString(), anyLong());
        verify(query, times(1)).getResultList();
        Assert.assertEquals(gettedComments.equals(comments), true);
    }



}
