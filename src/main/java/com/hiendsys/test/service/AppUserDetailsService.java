package com.hiendsys.test.service;

import com.hiendsys.test.dao.entity.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("appUserDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    UserService userService;


    public UserAccount getByUsername(String username) {
        return userService.getByUsername(username);
    }

    public Set<GrantedAuthority> getRole(UserAccount user) {
        Set<GrantedAuthority> roles = new HashSet<>();
        if (user instanceof UserAccount) {
            roles.add(new SimpleGrantedAuthority("USER"));
        }
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = getByUsername(username);
        if (user == null) {
            logger.info("user with email " + username + " not found");
            throw new UsernameNotFoundException("user with username" + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getRole(user));
    }
}
