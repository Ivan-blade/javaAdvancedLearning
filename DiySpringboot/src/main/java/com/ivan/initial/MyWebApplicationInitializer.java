package com.ivan.initial;


import com.ivan.config.AppConfig;
import com.ivan.controller.HelloController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author hylu.ivan
 * @date 2021/9/5 上午11:27
 * @description
 */
@Service
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        // Load Spring web application configuration
        //通过注解的方式初始化Spring的上下文
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        //注册spring的配置类（替代传统项目中xml的configuration）
        ac.register(AppConfig.class);
        ac.refresh();

        // Create and register the DispatcherServlet
        //基于java代码的方式初始化DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(ac);
        HelloController helloController = new HelloController();
        ServletRegistration.Dynamic registration = servletContext.addServlet("hello", helloController);
        registration.setLoadOnStartup(1);
        registration.addMapping("/hello");
    }
}
