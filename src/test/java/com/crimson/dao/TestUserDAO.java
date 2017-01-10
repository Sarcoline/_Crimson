package com.crimson.dao;

import com.crimson.context.TestSpringCore;
import com.crimson.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@WebAppConfiguration
@Transactional
@Rollback(value = false)
public class TestUserDAO {

    @Autowired
    UserDAO userDAO;

    User user = new User();

    @Before
    public void setDB(){
        user.setName("Alex");
        userDAO.saveUser(user);
    }

    @Test
    public void deleteUserTest(){
        //userDAO.deleteUser(user);

        //Assert.assertEquals(null, userDAO.getUserById(user.getId()));
    }
}
