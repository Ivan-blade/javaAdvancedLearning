package com.lagou.rpc.consumer.lifecycle;

import com.lagou.rpc.consumer.common.NodesManager;
import com.lagou.rpc.consumer.factory.IUserFactory;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hylu.ivan
 * @date 2021/9/25 下午1:06
 * @description
 */
@Component
public class SpringbootInit implements ApplicationRunner {

    @Autowired
    private IUserFactory iUserFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        zookeeperInit();
    }

    public void zookeeperInit() {

        ZkClient zkClient = new ZkClient("192.168.8.101:2181");
        String path = "/127.0.0.1";
        List<String> children = zkClient.getChildren(path);
        dealList(path,children);
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            /**
             * @param s 监听节点路径
             * @param list 变化后节点列表
             * @throws Exception
             */
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                dealList(path,list);
            }
        });
    }

    public void dealList(String path,List<String> list) {
        // 将zookeeper节点保存为ip port存放在容器中
        Stream<String> stringStream = list.stream().map((item) -> (path.replace("/","") + "\t" + item));
        List<String> collect = stringStream.collect(Collectors.toList());
        NodesManager.nodesList = collect;
        iUserFactory.updateUserDeque();
    }
}

