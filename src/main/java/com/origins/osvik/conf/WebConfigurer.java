package com.origins.osvik.conf;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by Amila-Kumara on 1/4/2016.
 */
public class WebConfigurer implements ServletContextListener {
    public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();
    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        this.log.debug("Configuring Spring root application context");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(new Class[]{ApplicationConfiguration.class});
        rootContext.refresh();

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootContext);

        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);

        initSpring(servletContext, rootContext);
        initMetrics(servletContext, disps);
        initSpringSecurity(servletContext, disps);

        this.log.debug("Web application fully configured");
    }

    private void initSpring(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        this.log.debug("Configuring Spring Web application context");
        AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        dispatcherServletConfiguration.setParent(rootContext);
        dispatcherServletConfiguration.register(new Class[]{DispatcherServletConfiguration.class});

        this.log.debug("Registering Spring MVC Servlet");
        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServletConfiguration));
        dispatcherServlet.addMapping(new String[]{"/app/*"});
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.setAsyncSupported(true);

        this.log.debug("Registering API Servlet");
        AnnotationConfigWebApplicationContext apiDispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        apiDispatcherServletConfiguration.setParent(rootContext);
        apiDispatcherServletConfiguration.register(new Class[]{ApiDispatcherServletConfiguration.class});

        ServletRegistration.Dynamic apiDispatcherServlet = servletContext.addServlet("apiDispatcher", new DispatcherServlet(apiDispatcherServletConfiguration));

        apiDispatcherServlet.addMapping(new String[]{"/api/*"});
        apiDispatcherServlet.setLoadOnStartup(1);
        apiDispatcherServlet.setAsyncSupported(true);
    }

    private void initSpringSecurity(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        this.log.debug("Registering Spring Security Filter");
        FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());

        springSecurityFilter.addMappingForUrlPatterns(disps, false, new String[]{"/*"});
        springSecurityFilter.setAsyncSupported(true);
    }

    private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        this.log.debug("Initializing Metrics registries");
        servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, METRIC_REGISTRY);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, METRIC_REGISTRY);
        servletContext.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY, HEALTH_CHECK_REGISTRY);

        this.log.debug("Registering Metrics Filter");
        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter", new InstrumentedFilter());

        metricsFilter.addMappingForUrlPatterns(disps, true, new String[]{"/*"});
        metricsFilter.setAsyncSupported(true);

        this.log.debug("Registering Metrics Admin Servlet");
        ServletRegistration.Dynamic metricsAdminServlet = servletContext.addServlet("metricsAdminServlet", new AdminServlet());

        metricsAdminServlet.addMapping(new String[]{"/metrics/*"});
        metricsAdminServlet.setLoadOnStartup(2);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        this.log.info("Destroying Web application");
        WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        AnnotationConfigWebApplicationContext gwac = (AnnotationConfigWebApplicationContext) ac;
        gwac.close();
        this.log.debug("Web application destroyed");
    }
}