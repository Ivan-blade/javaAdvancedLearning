package com.ivan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author hylu.ivan
 * @date 2021/9/12 下午9:55
 * @description
 */

@SpringBootApplication
@MapperScan("com.ivan.mapper")
@EnableRedisHttpSession
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }
}
