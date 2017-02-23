package com.crimson.core.service;

import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        com.crimson.core.model.User user = userDao.getUserByName(username);
        List<GrantedAuthority> authorities =
                buildUserAuthority(user);

        return buildUserForAuthentication(user, authorities);

    }

    //return springSecurity user
    private User buildUserForAuthentication(com.crimson.core.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getName(), user.getPassword(),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(com.crimson.core.model.User user) {
        List<GrantedAuthority> setAuths = new ArrayList<>();

        for (Role role : user.getRoles()) {
            setAuths.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role.getRoleName())));
        }

        return setAuths;
    }

}
