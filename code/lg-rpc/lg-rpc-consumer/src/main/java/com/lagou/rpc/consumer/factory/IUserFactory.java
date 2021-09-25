package com.lagou.rpc.consumer.factory;

import com.lagou.rpc.api.IUserService;
import com.lagou.rpc.consumer.common.NodesManager;
import com.lagou.rpc.consumer.proxy.RpcClientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hylu.ivan
 * @date 2021/9/21 下午2:43
 * @description
 */
@Component
public class IUserFactory {

    private Deque<IUserService> userDeque = new LinkedList<>();

    @Autowired
    private RpcClientProxy rpcClientProxy;

    public IUserService getUserService() {
        IUserService iUserService = userDeque.removeFirst();
        userDeque.addLast(iUserService);
        return iUserService;
    }

    public void updateUserDeque() {
        List<String> list = NodesManager.nodesList;
        userDeque.clear();
        for (String s : list) {
            String[] split = s.split("\t");
            String ip = split[0];
            int port = Integer.parseInt(split[1]);
            IUserService userService = (IUserService) rpcClientProxy.createProxy(IUserService.class,ip,port);
            userDeque.add(userService);
        }
        System.out.println("可用服务已装载到容器,数量为："+ userDeque.size());
    }
}
