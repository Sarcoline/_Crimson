package com.crimson.service;

import com.crimson.dao.UserDAO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserDAO userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        com.crimson.model.User user = userDao.getUserByName(username);
        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getRole());

        return buildUserForAuthentication(user, authorities);

    }

    //return springSecurity user
    private User buildUserForAuthentication(com.crimson.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getName(), user.getPassword(),
                true, true, true, true, authorities);
    }

    //zwracanie listy ról, na razie sztywne ROLE_USER
    private List<GrantedAuthority> buildUserAuthority(String role) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(role));
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}
