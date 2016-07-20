package com.origins.osvik.security;

import com.origins.osvik.domain.Authority;
import com.origins.osvik.domain.User;
import com.origins.osvik.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);
    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String login) {
        this.log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();

        User userFromDatabase = this.userRepository.findOne(login);
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList();
        for (Authority authority : userFromDatabase.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(lowercaseLogin, userFromDatabase.getPassword(), grantedAuthorities);
    }
}