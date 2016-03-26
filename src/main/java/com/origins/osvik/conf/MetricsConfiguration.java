package com.origins.osvik.conf;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.*;
import com.origins.osvik.conf.metrics.DatabaseHealthCheck;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.management.ManagementFactory;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
@Configuration
@EnableMetrics(proxyTargetClass = true)
public class MetricsConfiguration extends MetricsConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(MetricsConfiguration.class);
    @Autowired
    private Environment env;
    @Autowired
    private DataSource dataSource;

    public MetricRegistry getMetricRegistry() {
        return WebConfigurer.METRIC_REGISTRY;
    }

    public HealthCheckRegistry getHealthCheckRegistry() {
        return WebConfigurer.HEALTH_CHECK_REGISTRY;
    }

    @PostConstruct
    public void init() {
        this.log.debug("Registring JVM gauges");
        WebConfigurer.METRIC_REGISTRY.register("jvm.memory", new MemoryUsageGaugeSet());
        WebConfigurer.METRIC_REGISTRY.register("jvm.garbage", new GarbageCollectorMetricSet());
        WebConfigurer.METRIC_REGISTRY.register("jvm.threads", new ThreadStatesGaugeSet());
        WebConfigurer.METRIC_REGISTRY.register("jvm.files", new FileDescriptorRatioGauge());
        WebConfigurer.METRIC_REGISTRY.register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));

        this.log.debug("Initializing Metrics healthchecks");
        WebConfigurer.HEALTH_CHECK_REGISTRY.register("database", new DatabaseHealthCheck(this.dataSource));
    }

    public void configureReporters(MetricRegistry metricRegistry) {
        this.log.info("Initializing Metrics JMX metrics reporting");
        JmxReporter jmxReporter = JmxReporter.forRegistry(WebConfigurer.METRIC_REGISTRY).build();
        jmxReporter.start();
    }
}
