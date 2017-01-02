package com.crimson.dao;

import com.crimson.WebConfig;
import com.crimson.model.User;

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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class UserDAOTest {


    @Autowired
    UserDAO userDAO;

    @Autowired
    TvShowDAO tvShowDAO;

    private int usersSize;

    @Before
    public void size() {
        usersSize = userDAO.getAllUsers().size();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddUser() {
        User user = new User();
        user.setName("Paula");
        user.setEmail("paula@gmail.com");
        user.setPassword("abc");
        userDAO.saveUser(user);
        Assert.assertEquals(user.getName(), userDAO.getUserById(user.getId()).getName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteUser(){

        List<User> users = userDAO.getAllUsers();
        int userSizeBeforeDelete = users.size()-1;
        Long idUser = users.get(userDAO.getAllUsers().size()-1).getId();
        userDAO.deleteUser(userDAO.getUserById(idUser));

        Assert.assertEquals(null, userDAO.getUserById(idUser));
        Assert.assertEquals(userSizeBeforeDelete, userDAO.getAllUsers().size());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateUser(){

        List<User> users = userDAO.getAllUsers();
        Long idUser;
        if(users.size() == 0){
            testAddUser();
            users = userDAO.getAllUsers();
            idUser = users.get(userDAO.getAllUsers().size()-1).getId();
        }
        else{
            idUser = users.get(userDAO.getAllUsers().size()-1).getId();
        }
        userDAO.getUserById(idUser).setName("Update Test");
        userDAO.getUserById(idUser).setEmail("Update Test");
        userDAO.updateUser(userDAO.getUserById(idUser));

        Assert.assertEquals("Update Test", userDAO.getUserById(idUser).getName());
        Assert.assertEquals("Update Test", userDAO.getUserById(idUser).getEmail());

    }
}
