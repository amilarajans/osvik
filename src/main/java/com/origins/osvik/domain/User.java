package com.origins.osvik.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@Entity
@Table(name = "USER_INFO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    private String login;

    @JsonIgnore
    @Size(max = 100)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Email
    @Size(max = 100)
    private String email;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {@javax.persistence.JoinColumn(name = "login", referencedColumnName = "login")}, inverseJoinColumns = {@javax.persistence.JoinColumn(name = "name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> authorities;

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        User user = (User) o;
        return this.login.equals(user.login);
    }

    public int hashCode() {
        return this.login.hashCode();
    }

    public String toString() {
        return String.format("User{login='%s', password='%s', firstName='%s', lastName='%s', email='%s'}", this.login, this.password, this.firstName, this.lastName, this.email);
    }
}