package com.ivan.controller;

import com.ivan.service.CommonService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hylu.ivan
 * @date 2021/9/27 下午10:51
 * @description
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Reference
    private CommonService commonService;

    private ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10,20,5l, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());


    @RequestMapping("/hello")
    public String hello(String str) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 800; i++) {
                            commonService.hello01(str);
                            commonService.hello02(str);
                            commonService.hello03(str);
                            System.out.println("第"+i+"轮测试...");
                        }
                    }
                });
            }
        });
        thread.start();
        return "线程池正在执行...";
    }
}
