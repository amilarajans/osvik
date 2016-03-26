package com.origins.osvik.conf;

import com.origins.osvik.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            logger.error("Could not configure authentication mechanism:", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new UserDetailsService();
    }

    @Configuration
    @Order(10)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private Environment env;
        @Autowired
        private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
        @Autowired
        private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
        @Autowired
        private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;
        @Autowired
        private Http401UnauthorizedEntryPoint authenticationEntryPoint;

        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/bower_components/**").antMatchers("/additional_components/**").antMatchers("/fonts/**").antMatchers("/images/**").antMatchers("/scripts/**").antMatchers("/styles/**").antMatchers("/views/**");
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
                    .authenticationEntryPoint(this.authenticationEntryPoint)
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/app/authentication")
                    .successHandler(this.ajaxAuthenticationSuccessHandler)
                    .failureHandler(this.ajaxAuthenticationFailureHandler)
                    .usernameParameter("j_username").passwordParameter("j_password").permitAll().and()
                    .logout().logoutUrl("/app/logout").logoutSuccessHandler(this.ajaxLogoutSuccessHandler)
                    .deleteCookies(new String[]{"JSESSIONID"}).permitAll().and().csrf().disable().authorizeRequests()
                    .antMatchers("/*").permitAll()
                    .antMatchers("/app/rest/authenticate").permitAll()
                    .antMatchers("/app/rest/logs/**").hasRole("ADMIN")
                    .antMatchers("/app/**").hasRole("ADMIN")
                    .antMatchers("/metrics/**").hasRole("ADMIN");
        }
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable()
                    .antMatcher("/api/**").authorizeRequests().antMatchers("/api/**").hasAnyRole(new String[]{"CLUSTER_MANAGER", "ADMIN"})
                    .and().httpBasic();
        }
    }
}
