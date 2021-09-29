package com.ivan.service;

import org.apache.dubbo.config.annotation.Service;

import java.util.Random;

/**
 * @author hylu.ivan
 * @date 2021/9/27 下午10:48
 * @description
 */
@Service
public class CommonServiceImpl implements CommonService{

    private Random random = new Random();

    @Override
    public String hello01(String str) {
        sleep();
        return str+"01";
    }

    @Override
    public String hello02(String str) {
        sleep();
        return str+"02";
    }

    @Override
    public String hello03(String str) {
        sleep();
        return str+"03";
    }

    public void sleep() {
        int i = random.nextInt(100) + 1;
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
