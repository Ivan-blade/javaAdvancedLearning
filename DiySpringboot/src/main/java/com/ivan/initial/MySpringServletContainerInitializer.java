package com.ivan.initial;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author hylu.ivan
 * @date 2021/9/5 下午8:13
 * @description
 */
public class MySpringServletContainerInitializer implements ServletContainerInitializer {

    @Autowired
    private MyWebApplicationInitializer myWebApplicationInitializer;

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        myWebApplicationInitializer.onStartup(servletContext);
    }
}
