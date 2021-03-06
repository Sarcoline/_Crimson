package com.crimson.core.functional.dao;


import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.RoleDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.factory.RoleFactory;
import com.crimson.core.factory.UserFactory;
import com.crimson.core.model.Role;
import com.crimson.core.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback()
public class TestRoleDAO {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private UserDAO userDAO;

    private RoleFactory roleFactory = new RoleFactory();
    private UserFactory userFactory = new UserFactory();


    private Role role1 = roleFactory.getRole("role_1");
    private Role role2 = roleFactory.getRole("role_2");
    private Role role3 = roleFactory.getRole("role_3");

    private User user1 = userFactory.getUser("aleks");
    private User user2 = userFactory.getUser("kamil");


    @Before
    public void setDB() {
        roleDAO.save(role1);
        roleDAO.save(role2);
        roleDAO.save(role3);

        userDAO.save(user1);
        userDAO.save(user2);
    }


    @Test
    public void saveTest() {
        Role role = Role.builder()
                .roleName("ROLE_USER")
                .build();

        roleDAO.save(role);

        Assert.assertEquals(role.getRoleName(), roleDAO.getById(role.getIdRole()).getRoleName());
        Assert.assertEquals(roleDAO.getAll().contains(role), true);
    }

    @Test
    public void updateTest() {
        role1.setRoleName("NEW ROLE");
        roleDAO.update(role1);

        Assert.assertEquals(role1.getRoleName(), roleDAO.getById(role1.getIdRole()).getRoleName());
    }

    @Test
    public void deleteTest() {
        int listSize = roleDAO.getAll().size();

        roleDAO.delete(role1);

        Assert.assertEquals(roleDAO.getAll().contains(role1), false);
        Assert.assertEquals(listSize - 1, roleDAO.getAll().size());
    }

    @Test
    public void getAllRolesTest() {
        int listSize = roleDAO.getAll().size();

        Assert.assertEquals(listSize, 3);
    }

    @Test
    public void getRoleByIdTest() {
        Role tmpRole = roleDAO.getById(role1.getIdRole());

        Assert.assertEquals(tmpRole.equals(role1), true);
    }

    @Test
    public void getUsersTest(){
        role1.getUsers().add(user1);
        user1.getRoles().add(role1);

        Assert.assertEquals(roleDAO.getUsers(role1),role1.getUsers());
    }
}
