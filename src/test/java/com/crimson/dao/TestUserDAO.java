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
@Rollback(value = true)
public class TestUserDAO {

    @Autowired
    private UserDAO userDAO;

    private User user = new User();

    @Before
    public void setDB(){
        user.setName("Alex");
        userDAO.saveUser(user);
    }

    @Test
    public void addUserTest(){
        user.setName("Alex");
        user.setEmail("Alex@wp.pl");
        user.setPassword("1234");
        user.setProfilePicLocation("google.pl");
        user.setRole("ROLE_ADMIN");

        userDAO.saveUser(user);

        Assert.assertEquals(user.getName(), userDAO.getUserById(user.getId()).getName());
        Assert.assertEquals(user.getEmail(), userDAO.getUserById(user.getId()).getEmail());
        Assert.assertEquals(user.getPassword(), userDAO.getUserById(user.getId()).getPassword());
        Assert.assertEquals(1, userDAO.getAllUsers().size());
    }

    @Test
    public void updateUserTest(){
        user.setEmail("changeEmail");
        user.setPassword("changePassword");

        userDAO.updateUser(user);

        Assert.assertEquals(user.getEmail(), "changeEmail");
        Assert.assertEquals(user.getPassword(), "changePassword");
    }

    @Test
    public void deleteUserTest(){
        userDAO.deleteUser(user);

        Assert.assertEquals(null, userDAO.getUserById(user.getId()));
    }

    @Test
    public void getAllUsersTest(){
        int usersListSize = userDAO.getAllUsers().size();

        Assert.assertEquals(1, usersListSize);
    }

    @Test
    public void getUserByIdTest(){
        User getUserByIdTest = userDAO.getUserById(user.getId());

        Assert.assertEquals(getUserByIdTest.getName(), user.getName());
    }

    @Test
    public void getUserByNameTest(){
        User getUserByNameTest = userDAO.getUserByName("Alex");

        Assert.assertEquals(getUserByNameTest.getName(), user.getName());
    }
}
