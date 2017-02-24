package com.crimson.core.dao;


import com.crimson.context.TestSpringCore;
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
    public void setDB(){
        roleDAO.save(role1);
        roleDAO.save(role2);
        roleDAO.save(role3);

        userDAO.save(user1);
        userDAO.save(user2);
    }


    @Test
    public void saveTest(){
        Role role = Role.builder()
                .roleName("ROLE_USER")
                .build();

        roleDAO.save(role);

        Assert.assertEquals(role.getRoleName(), roleDAO.getRoleById(role.getIdRole()).getRoleName());
        Assert.assertEquals(roleDAO.getAllRoles().contains(role), true);
    }

    @Test
    public void updateTest(){
        role1.setRoleName("NEW ROLE");
        roleDAO.update(role1);

        Assert.assertEquals(role1.getRoleName(), roleDAO.getRoleById(role1.getIdRole()).getRoleName());
    }

    @Test
    public void deleteTest(){
        int listSize = roleDAO.getAllRoles().size();

        roleDAO.delete(role1);

        Assert.assertEquals(roleDAO.getAllRoles().contains(role1), false);
        Assert.assertEquals(listSize-1, roleDAO.getAllRoles().size());
    }

    @Test
    public void getAllRolesTest(){
        int listSize = roleDAO.getAllRoles().size();

        Assert.assertEquals(listSize, 3);
    }

    @Test
    public void getRoleByIdTest(){
        Role tmpRole = roleDAO.getRoleById(role1.getIdRole());

        Assert.assertEquals(tmpRole.equals(role1), true);
    }

    @Test
    public void addUser2RoleTest(){
        int listSize = role1.getUsers().size();

        roleDAO.addUser2Role(user1, role1);

        Assert.assertEquals(listSize+1, role1.getUsers().size());
        Assert.assertEquals(role1.getUsers().contains(user1), true);
        Assert.assertEquals(listSize+1, roleDAO.getRoleById(role1.getIdRole()).getUsers().size());
        Assert.assertEquals(roleDAO.getRoleById(role1.getIdRole()).getUsers().contains(user1), true);
    }

    @Test
    public void deleteUserFromRoleTest(){
        addUser2RoleTest();
        int listSize = role1.getUsers().size();

        roleDAO.deleteUserFromRole(user1, role1);

        Assert.assertEquals(listSize-1, role1.getUsers().size());
        Assert.assertEquals(role1.getUsers().contains(user1), false);
        Assert.assertEquals(listSize-1, roleDAO.getRoleById(role1.getIdRole()).getUsers().size());
        Assert.assertEquals(roleDAO.getRoleById(role1.getIdRole()).getUsers().contains(user1), false);
    }
}
