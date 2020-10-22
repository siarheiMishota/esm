package com.epam.esm;

import com.epam.esm.configuration.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);

    }
}
