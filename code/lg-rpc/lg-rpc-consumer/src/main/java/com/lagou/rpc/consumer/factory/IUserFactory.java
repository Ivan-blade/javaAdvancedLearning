package com.lagou.rpc.consumer.factory;

import com.lagou.rpc.api.IUserService;
import com.lagou.rpc.consumer.proxy.RpcClientProxy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author hylu.ivan
 * @date 2021/9/21 下午2:43
 * @description
 */
public class IUserFactory {

    public static Deque<IUserService> deque = new LinkedList<>();

    static {
        IUserService userService1 = (IUserService) RpcClientProxy.createProxy(IUserService.class,"127.0.0.1",8899);
        IUserService userService2 = (IUserService) RpcClientProxy.createProxy(IUserService.class,"127.0.0.1",8898);
        IUserService userService3 = (IUserService) RpcClientProxy.createProxy(IUserService.class,"127.0.0.1",8897);
        IUserFactory.deque.add(userService1);
        IUserFactory.deque.add(userService2);
        IUserFactory.deque.add(userService3);
        System.out.println("可用服务已装载到容器,数量为："+IUserFactory.deque.size());
    }

    public static IUserService getUserService() {
        IUserService iUserService = deque.removeFirst();
        deque.addLast(iUserService);
        return iUserService;
    }
}
