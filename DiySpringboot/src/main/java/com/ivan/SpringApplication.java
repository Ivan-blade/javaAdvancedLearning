package com.ivan;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletException;
import java.io.File;

/**
 * @author hylu.ivan
 * @date 2021/9/5 下午1:45
 * @description
 */
public class SpringApplication {


    public static int TOMCAT_PORT = 8080;
    public static String TOMCAT_HOSTNAME = "127.0.0.1";
    public static String WEBAPP_PATH = "src/main";

    public static void run() {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(SpringApplication.TOMCAT_PORT);
        tomcat.setHostname(SpringApplication.TOMCAT_HOSTNAME);
        tomcat.setBaseDir("."); // tomcat 信息保存在项目下

        StandardContext myCtx = null;
        try {
            myCtx = (StandardContext) tomcat.addWebapp("", System.getProperty("user.dir") + File.separator + SpringApplication.WEBAPP_PATH);
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
