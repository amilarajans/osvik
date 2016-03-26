package com.origins.osvik.conf.metrics;

import com.codahale.metrics.health.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
public class DatabaseHealthCheck extends HealthCheck {
    private final Logger log = LoggerFactory.getLogger(HealthCheck.class);
    private JdbcTemplate jdbcTemplate;

    public DatabaseHealthCheck(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Result check() {
        try {
            this.jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Result.healthy();
        } catch (Exception e) {
            this.log.debug("Cannot connect to Database: {}", e);
            return Result.unhealthy("Cannot connect to Database : " + e.getMessage());
        }
    }
}
