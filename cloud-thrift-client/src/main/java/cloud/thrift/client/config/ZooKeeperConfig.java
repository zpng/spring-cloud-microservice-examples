/**
 * @(#)ZooKeeperConfig.java, 十一月 02, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.thrift.client.config;

import cloud.thrift.UserService;
import org.I0Itec.zkclient.IZkChildListener;
import org.apache.helix.manager.zk.ZkClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangpeng
 */

@Configuration
public class ZooKeeperConfig {

    @Value("${service.name}")
    String serviceName;

    @Value("${zookeeper.server.list}")
    String zookeeperList;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    // thrift实例列表
    public static Map<String, UserService.Client> serviceMap = new HashMap<String, UserService.Client>();


    @PostConstruct
    private void init() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                startZooKeeper();
                try {
                    Thread.sleep(1000 * 60 * 60 * 24 * 360 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 注册服务
    private void startZooKeeper() {
        List<String> currChilds = new ArrayList<String>();
        String servicePath = "/" + serviceName;// 根节点路径
        ZkClient zkClient = new ZkClient(zookeeperList);
        boolean serviceExists = zkClient.exists(servicePath);
        if (serviceExists) {
            currChilds = zkClient.getChildren(servicePath);
        } else {
            throw new RuntimeException("service not exist!");
        }

        for (String instanceName : currChilds) {
            // 没有该服务，建立该服务
            if (!serviceMap.containsKey(instanceName)) {
                serviceMap.put(instanceName, createUserService(instanceName));
            }
        }
        // 注册事件监听
        zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
            // @Override
            public void handleChildChange(String parentPath,
                                          List<String> currentChilds) throws Exception {
                // 实例(path)列表:当某个服务实例宕机，实例列表内会减去该实例
                for (String instanceName : currentChilds) {
                    // 没有该服务，建立该服务
                    if (!serviceMap.containsKey(instanceName)) {
                        serviceMap.put(instanceName,createUserService(instanceName));
                    }
                }
                for (Map.Entry<String, UserService.Client> entry : serviceMap.entrySet()) {
                    // 该服务已被移除
                    if (!currentChilds.contains(entry.getKey())) {
                        UserService.Client c = serviceMap.get(entry.getKey());
                        try {
                            c.getInputProtocol().getTransport().close();
                            c.getOutputProtocol().getTransport().close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        serviceMap.remove(entry.getKey());
                    }
                }
                System.out.println(parentPath + "事件触发");
            }
        });
    }

    // 创建一个服务实例
    private UserService.Client createUserService(String serviceInstanceName) {
        String ip = serviceInstanceName.split("-")[1];
        TSocket transport = new TSocket(ip, 7911);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        return new UserService.Client(new TBinaryProtocol(transport));
    }
}