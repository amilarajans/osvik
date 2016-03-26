package com.origins.osvik.dto;

import com.origins.osvik.domain.Authority;
import com.origins.osvik.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public class UserRepresentation {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> authorities = new ArrayList();

    public UserRepresentation() {
    }

    public UserRepresentation(User user) {
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        if (user.getAuthorities() != null) {
            for (Authority authority : user.getAuthorities()) {
                this.authorities.add(authority.getName());
            }
        }
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}