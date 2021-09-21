package com.lagou.rpc.consumer.controller;

import com.lagou.rpc.api.IUserService;
import com.lagou.rpc.consumer.factory.IUserFactory;
import com.lagou.rpc.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hylu.ivan
 * @date 2021/9/21 下午2:05
 * @description
 */
@RestController
@RequestMapping("/query")
public class QueryController {

    @RequestMapping("/user")
    public User queryUserById(String id) {
        IUserService userService = IUserFactory.getUserService();
        User user = userService.getById(1);
        return user;
    }

}
