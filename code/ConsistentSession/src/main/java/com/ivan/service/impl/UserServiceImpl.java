package com.ivan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivan.mapper.UserMapper;
import com.ivan.pojo.User;
import com.ivan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hylu.ivan
 * @date 2021/9/12 下午10:28
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Integer reg(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        int insert = userMapper.insert(user);
//        由于在User类的id属性上设置了主键自增策略，数据插入成功后生成的id会被封装在传入user类的id属性中
//        System.out.println(user.getId());
        return insert;
    }
}
