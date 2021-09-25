package com.lagou.rpc.provider.lifecycle;

import com.lagou.rpc.provider.server.RpcServer;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author hylu.ivan
 * @date 2021/9/25 下午1:19
 * @description
 */
@Component
public class SpringInit implements ApplicationRunner {

    @Autowired
    RpcServer rpcServer;

    @Value("${server.port}")
    private int port;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = "127.0.0.1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                rpcServer.startServer(ip, port);
            }
        }).start();
        zookeeperInit(ip,port);
    }

    public void zookeeperInit(String ip,int port) {
        String t = "/";
        ZkClient zkClient = new ZkClient("192.168.8.101:2181");
        String nodePath = t + ip + t + port;
        if (!zkClient.exists(nodePath)) {
            zkClient.createPersistent(nodePath, true);
            System.out.println(nodePath+"节点注册完成");
        }
    }
}
