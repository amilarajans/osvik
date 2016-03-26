package com.origins.osvik.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Component
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendError(401, "Authentication failed");
    }
}
