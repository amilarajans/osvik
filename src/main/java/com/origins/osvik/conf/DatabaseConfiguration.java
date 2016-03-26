package com.origins.osvik.conf;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@Configuration
@EnableJpaRepositories({"com.origins.osvik.repository"})
@EnableTransactionManagement
public class DatabaseConfiguration {
    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        this.log.info("Configuring Datasource");

        String dataSourceJndiName = this.env.getProperty("datasource.jndi.name");
        if (StringUtils.isNotEmpty(dataSourceJndiName)) {
            this.log.info("Using jndi datasource '" + dataSourceJndiName + "'");
            JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
            dsLookup.setResourceRef(this.env.getProperty("datasource.jndi.resourceRef", Boolean.class, Boolean.TRUE).booleanValue());
            DataSource dataSource = dsLookup.getDataSource(dataSourceJndiName);
            return dataSource;
        }
        String dataSourceDriver = this.env.getProperty("datasource.driver", "com.mysql.jdbc.Driver");
        String dataSourceUrl = this.env.getProperty("datasource.url", "jdbc:mysql://127.0.0.1:3306/activitiadmin?characterEncoding=UTF-8");

        String dataSourceUsername = this.env.getProperty("datasource.username", "alfresco");
        String dataSourcePassword = this.env.getProperty("datasource.password", "alfresco");

        Integer minPoolSize = this.env.getProperty("datasource.min-pool-size", Integer.class);
        if (minPoolSize == null) {
            minPoolSize = Integer.valueOf(5);
        }
        Integer maxPoolSize = this.env.getProperty("datasource.max-pool-size", Integer.class);
        if (maxPoolSize == null) {
            maxPoolSize = Integer.valueOf(20);
        }
        Integer acquireIncrement = this.env.getProperty("datasource.acquire-increment", Integer.class);
        if (acquireIncrement == null) {
            acquireIncrement = Integer.valueOf(1);
        }
        String preferredTestQuery = this.env.getProperty("datasource.preferred-test-query");

        Boolean testConnectionOnCheckin = this.env.getProperty("datasource.test-connection-on-checkin", Boolean.class);
        if (testConnectionOnCheckin == null) {
            testConnectionOnCheckin = Boolean.valueOf(true);
        }
        Boolean testConnectionOnCheckOut = this.env.getProperty("datasource.test-connection-on-checkout", Boolean.class);
        if (testConnectionOnCheckOut == null) {
            testConnectionOnCheckOut = Boolean.valueOf(true);
        }
        Integer maxIdleTime = this.env.getProperty("datasource.max-idle-time", Integer.class);
        if (maxIdleTime == null) {
            maxIdleTime = Integer.valueOf(1800);
        }
        Integer maxIdleTimeExcessConnections = this.env.getProperty("datasource.max-idle-time-excess-connections", Integer.class);
        if (maxIdleTimeExcessConnections == null) {
            maxIdleTimeExcessConnections = Integer.valueOf(1800);
        }
        if (this.log.isInfoEnabled()) {
            this.log.info("Configuring Datasource with following properties (omitted password for security)");
            this.log.info("datasource driver: " + dataSourceDriver);
            this.log.info("datasource url : " + dataSourceUrl);
            this.log.info("datasource user name : " + dataSourceUsername);
            this.log.info("Min pool size | Max pool size | acquire increment : " + minPoolSize + " | " + maxPoolSize + " | " + acquireIncrement);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dataSourceDriver);
        hikariConfig.setJdbcUrl(dataSourceUrl);
        hikariConfig.setUsername(dataSourceUsername);
        hikariConfig.setPassword(dataSourcePassword);

        hikariConfig.setMaximumPoolSize(maxPoolSize.intValue());
        hikariConfig.setIdleTimeout(maxIdleTime.intValue());

        if (preferredTestQuery != null) {
            hikariConfig.setConnectionTestQuery(preferredTestQuery);
        }
        hikariConfig.setPoolName("origins");

        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

        HikariDataSource ds = new HikariDataSource(hikariConfig);
        return ds;
    }

    @Bean
    @DependsOn("flyway")
    public EntityManagerFactory entityManagerFactory() {
        this.log.debug("Configuring EntityManager");
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setPersistenceProvider(new HibernatePersistenceProvider());
        lcemfb.setPersistenceUnitName("persistenceUnit");
        lcemfb.setDataSource(dataSource());
        lcemfb.setJpaDialect(new HibernateJpaDialect());
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.cache.use_second_level_cache", Boolean.valueOf(false));
        jpaProperties.put("hibernate.generate_statistics", this.env.getProperty("hibernate.generate_statistics", Boolean.class, Boolean.valueOf(false)));
        jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");
        lcemfb.setJpaProperties(jpaProperties);

        lcemfb.setPackagesToScan("com.origins.osvik.domain");
        lcemfb.afterPropertiesSet();
        return lcemfb.getObject();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(this.env.getProperty("hibernate.show_sql", Boolean.class, Boolean.valueOf(false)).booleanValue());
        jpaVendorAdapter.setDatabasePlatform(this.env.getProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect"));
        return jpaVendorAdapter;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean(name = {"transactionManager"})
    @DependsOn("flyway")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        return jpaTransactionManager;
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:db/migration");
        flyway.setTable("SCHEMA_VERSION");
        flyway.setDataSource(dataSource());
        return flyway;
    }
}