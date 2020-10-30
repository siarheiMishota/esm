package com.epam.esm.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.scan("com.epam.esm");
        servletContext.addListener(new ContextLoaderListener(webApplicationContext));

        webApplicationContext.setServletContext(servletContext);
        webApplicationContext.register(WebConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext
            .addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
