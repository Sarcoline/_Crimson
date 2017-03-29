package com.crimson.core.unit.service;

import com.crimson.context.TestSpringCore;
import com.crimson.core.dao.RoleDAO;
import com.crimson.core.dao.UserDAO;
import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.User;
import com.crimson.core.model.Role;
import com.crimson.core.service.UserServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = TestSpringCore.class)
@Transactional
@Rollback
@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

    @Mock
    private UserDAO userDAO;
    @Mock
    private RoleDAO roleDAO;
    @Mock
    private MapperFacade mapperFacade;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private ApplicationContext context;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Before
    public void setUp(){

    }

    @Test
    public void getAllUserTest() throws IOException {
        doNothing().when(userDAO).addRole2User(Matchers.anyObject(),Matchers.anyObject());

        userService.getAllUsers();

        Mockito.verify(userDAO, Mockito.times(1)).getAll();
    }

    @Test
    public void saveUserTest() throws  IOException {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name123");
        userDTO.setPassword("password");
        User userD = User.builder().name("Test").build();
        Role role = Role.builder().roleName("USER_ROLE").build();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        // Resource resource = context.getResource(" ");
        // URL url = new URL(null, "classpath:/images/user/user.jpg");

        doNothing().when(roleDAO).addUser2Role(Matchers.anyObject(),Matchers.anyObject());
        when(roleDAO.getAll()).thenReturn(roles);
        when(mapperFacade.map(Matchers.anyObject(),Matchers.anyObject())).thenReturn(userD);
        when(encoder.encode(anyString())).thenReturn("password");
        //when(context.getResource(anyString())).thenReturn(resource);

        userService.saveUser(userDTO);

        Mockito.verify(userDAO, Mockito.times(1)).save(Matchers.anyObject());
    }

}
