package com.origins.osvik.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private final Logger log = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {
        this.log.debug("Pre-authenticated entry point called. Rejecting access");
        response.sendError(401, "Access Denied");
    }
}
