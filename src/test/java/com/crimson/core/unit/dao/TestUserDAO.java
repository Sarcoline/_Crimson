package com.crimson.core.unit.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.TvShowDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.TvShow;
import com.crimson.core.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback
@RunWith(MockitoJUnitRunner.class)
public class TestUserDAO {

    private static UserDAO userDAO;
    private static TvShowDAO tvShowDAO;


    private static User user;
    private static User user2;
    private static TvShow tvShow;
    private static TvShow tvShow2;

    @BeforeClass
    public static void setUp(){
        userDAO = mock(UserDAO.class);
        tvShowDAO = mock(TvShowDAO.class);
        user = User.builder().name("Test").build();
        user2 = User.builder().name("Test").build();
        tvShow = TvShow.builder().build();
        tvShow2 = TvShow.builder().build();

        when(userDAO.getAll()).thenReturn(Arrays.asList(user, user2));
        when(userDAO.getById(user.getId())).thenReturn(user);
        when(userDAO.getUserByName(user.getName())).thenReturn(user);
        when(userDAO.getUserByEmail(user.getEmail())).thenReturn(user);
        when(userDAO.getUserByToken(user.getToken())).thenReturn(user);
        when(userDAO.getTvShows(user)).thenReturn(Arrays.asList(tvShow, tvShow2));
    }

    @Test
    public void getTvShowsTest() throws Exception{
        List<TvShow> tvShows = userDAO.getTvShows(user);
        Assert.assertEquals(2, tvShows.size());
        Mockito.verify(userDAO, Mockito.times(1)).getTvShows(user);
    }

    @Test
    public void getAllTest() throws Exception{
        List<User> allUsers = userDAO.getAll();
        Assert.assertEquals(2, allUsers.size());
        Mockito.verify(userDAO, Mockito.times(1)).getAll();
    }

    @Test
    public void getByIdTest() throws Exception{
        User userTmp = userDAO.getById(user.getId());
        Assert.assertEquals(userTmp.equals(user), true);
        Mockito.verify(userDAO, Mockito.times(1)).getById(user.getId());
    }

    @Test
    public void getUserByName() throws Exception{
        User userTmp = userDAO.getUserByName(user.getName());
        Assert.assertEquals(userTmp.equals(user), true);
        Mockito.verify(userDAO, Mockito.times(1)).getUserByName(user.getName());
    }

    @Test
    public void getUserByEmailTest() throws Exception{
        User userTmp = userDAO.getUserByEmail(user.getEmail());
        Assert.assertEquals(userTmp.equals(user), true);
        Mockito.verify(userDAO, Mockito.times(1)).getUserByEmail(user.getEmail());
    }

    @Test
    public void getUserByTokenTest() throws Exception{
        User userTmp = userDAO.getUserByToken(user.getToken());
        Assert.assertEquals(userTmp.equals(user), true);
        Mockito.verify(userDAO, Mockito.times(1)).getUserByToken(user.getToken());
    }
}
