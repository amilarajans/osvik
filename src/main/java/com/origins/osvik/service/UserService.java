package com.origins.osvik.service;

import com.origins.osvik.domain.Authority;
import com.origins.osvik.domain.User;
import com.origins.osvik.dto.UserRepresentation;
import com.origins.osvik.repository.AuthorityRepository;
import com.origins.osvik.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@Service
@Transactional
public class UserService {
    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public User createAdminUser(String login, String password, String firstName, String lastName, String email) {
        return createUser(login, password, firstName, lastName, email, ADMIN_AUTHORITY);
    }

    public User createUser(String login, String password, String firstName, String lastName, String email, String... authorities) {
        if ((StringUtils.isEmpty(login)) || (StringUtils.isEmpty(password))) {
            throw new IllegalArgumentException("Both username and password are required");
        }
        User existing = this.userRepository.findOne(login);
        if (existing == null) {
            User user = new User();

            user.setPassword(this.passwordEncoder.encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLogin(login);
            if ((authorities != null) && (authorities.length > 0)) {
                for (String authority : authorities) {
                    Authority authorityEntity = this.authorityRepository.findOne(authority);
                    if (authorityEntity != null) {
                        if (user.getAuthorities() == null) {
                            user.setAuthorities(new HashSet());
                        }
                        user.getAuthorities().add(authorityEntity);
                    } else {
                        this.log.warn("Programmatic error: could not find authority entity for '" + authority + "'");
                    }
                }
            }
            this.userRepository.save(user);
            this.userRepository.flush();
            return user;
        }
        return null;
    }

    public void changePassword(String login, String oldPassword, String newPassword) {
        User currentUser = this.userRepository.findOne(login);
        if (!this.passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new RuntimeException("Old password is not correct");
        }
        String newEncryptedPassword = this.passwordEncoder.encode(newPassword);
        currentUser.setPassword(newEncryptedPassword);
        this.userRepository.save(currentUser);
        this.log.debug("Changed password for User: {}", currentUser);
    }

    public void deleteUser(String login) {
        User user = this.userRepository.findOne(login);
        if (user != null) {
            this.userRepository.delete(user);
        }
    }

    public List<UserRepresentation> getAllUsers() {
        List<User> users = this.userRepository.findAll(new Sort(new String[]{"login"}));
        List<UserRepresentation> result = new ArrayList(users.size());
        for (User user : users) {
            result.add(new UserRepresentation(user));
        }
        return result;
    }
}