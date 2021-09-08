package com.ivan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * @author hylu.ivan
 * @date 2021/9/5 下午12:12
 * @description
 */
@Configuration
@ComponentScan("com.ivan")
public class AppConfig {

    {
        System.out.println("component scan ...");
    }
}
