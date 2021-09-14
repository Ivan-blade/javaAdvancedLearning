package com.ivan.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hylu.ivan
 * @date 2021/9/14 下午8:53
 * @description
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/port")
    public String back() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getLocalAddr()+request.getLocalPort();
    }
}
