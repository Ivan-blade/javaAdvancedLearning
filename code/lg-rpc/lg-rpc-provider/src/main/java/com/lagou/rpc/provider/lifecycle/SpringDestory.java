package com.lagou.rpc.provider.lifecycle;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hylu.ivan
 * @date 2021/9/25 下午1:19
 * @description
 */
@Component
public class SpringDestory implements DisposableBean {

    @Value("${server.port}")
    private int port;

    @Override
    public void destroy() throws Exception {
        String ip = "127.0.0.1";
        zookeeperDelete(ip);
    }

    public void zookeeperDelete(String ip) {
        String t = "/";
        ZkClient zkClient = new ZkClient("192.168.8.101:2181");
        String nodePath = t + ip + t + port;
        zkClient.delete(nodePath);
        System.out.println(nodePath+"节点删除完成！");
    }
}
