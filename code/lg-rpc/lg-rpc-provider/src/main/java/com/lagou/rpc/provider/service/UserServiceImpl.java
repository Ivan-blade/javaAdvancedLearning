package com.lagou.rpc.provider.service;

import com.lagou.rpc.api.IUserService;
import com.lagou.rpc.pojo.User;
import com.lagou.rpc.provider.anno.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RpcService
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    Environment environment;

    Map<Object, User> userMap = new HashMap();

    @Override
    public User getById(int id) {
        if (userMap.size() == 0) {
            User user1 = new User();
            user1.setId(1);
            user1.setName("张三");
            User user2 = new User();
            user2.setId(2);
            user2.setName("李四");
            userMap.put(user1.getId(), user1);
            userMap.put(user2.getId(), user2);
        }
        System.out.println(userMap.get(id));
        return userMap.get(id);
    }
}