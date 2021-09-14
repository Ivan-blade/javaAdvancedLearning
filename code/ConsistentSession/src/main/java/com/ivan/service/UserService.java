package com.ivan.service;

import com.ivan.pojo.User;

/**
 * @author hylu.ivan
 * @date 2021/9/12 下午10:11
 * @description
 */
public interface UserService {

    User login(String username,String password);

    Integer reg(String username,String password);
}
