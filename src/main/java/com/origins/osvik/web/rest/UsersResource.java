package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.origins.osvik.domain.User;
import com.origins.osvik.dto.UserRepresentation;
import com.origins.osvik.service.UserService;
import com.origins.osvik.web.rest.exception.BadRequestException;
import com.origins.osvik.web.rest.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
public class UsersResource {
    private final Logger log = LoggerFactory.getLogger(UsersResource.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/rest/users"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<UserRepresentation> getUsers() {
        return this.userService.getAllUsers();
    }

    @RequestMapping(value = {"/rest/users"}, method = {RequestMethod.POST})
    @Timed
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createUser(@RequestBody ObjectNode actionBody) {
        this.log.debug("REST request to create a User : {}");
        if ((!actionBody.get("login").isNull()) && (!actionBody.get("password").isNull())) {
            try {
                User result = this.userService.createAdminUser(actionBody.get("login").asText(), actionBody.get("password").asText(), actionBody.get("firstName").asText(), actionBody.get("lastName").asText(), actionBody.get("email").asText());
                if (result == null) {
                    throw new ConflictException("User with login '" + actionBody.get("login").asText() + "' already exists.");
                }
            } catch (IllegalArgumentException iae) {
                throw new BadRequestException(iae.getMessage());
            }
        } else {
            throw new BadRequestException("login and password are required " + actionBody);
        }
    }
}