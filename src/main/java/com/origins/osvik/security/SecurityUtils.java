package com.origins.osvik.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
public final class SecurityUtils {
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails springSecurityUser = (UserDetails) securityContext.getAuthentication().getPrincipal();
        return springSecurityUser.getUsername();
    }
}
