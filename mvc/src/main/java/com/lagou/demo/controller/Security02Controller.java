package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hylu.Ivan
 * @date 2021/7/27 下午11:20
 * @description
 */
@LagouController
@LagouRequestMapping("/security02")
public class Security02Controller {

    @LagouAutowired
    private IDemoService demoService;


    @LagouRequestMapping("/query01")
    @Security("")
    public String query01(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.get(name);
    }

    @LagouRequestMapping("/query02")
    @Security
    public String query02(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.get(name);
    }
}
