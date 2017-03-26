package com.crimson.core.unit.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
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


    @BeforeClass
    public static void setUp(){
        userDAO = mock(UserDAO.class);
        User user = User.builder().name("Test").build();
        User user2 = User.builder().name("Test").build();

        when(userDAO.getAll()).thenReturn(Arrays.asList(user, user2));
    }

    @Test
    public void getAllTest() throws Exception{
        List<User> allUsers = userDAO.getAll();
        Assert.assertEquals(2, allUsers.size());
    }

}
