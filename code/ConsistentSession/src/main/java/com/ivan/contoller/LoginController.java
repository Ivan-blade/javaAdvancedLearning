package com.ivan.contoller;

import com.ivan.pojo.User;
import com.ivan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hylu.ivan
 * @date 2021/9/12 下午9:56
 * @description
 */

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public int register(User user) {
        return userService.reg(user.getUsername(),user.getPassword());
    }

    @RequestMapping("/login")
    public User login(User user) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        User login = userService.login(user.getUsername(), user.getPassword());
        if (login != null) request.getSession().setAttribute("username",login.getUsername());
        else request.getSession().setAttribute("username",null);
        return login;
    }
}
