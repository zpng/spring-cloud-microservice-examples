/**
 * @(#)UserServiceProvider.java, 十一月 02, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.thrift.client.provider;

import cloud.thrift.UserService;
import cloud.thrift.client.config.ZooKeeperConfig;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * @author zhangpeng
 */
@Component
public class UserServiceProvider {

    public UserService.Client getBalanceUserService(){
        Map<String, UserService.Client> serviceMap = ZooKeeperConfig.serviceMap;
        //以负载均衡的方式获取服务实例
        for (Map.Entry<String, UserService.Client> entry : serviceMap.entrySet()) {
            System.out.println("可供选择服务:"+entry.getKey());
        }
        int rand=new Random().nextInt(serviceMap.size());
        String[] mkeys = serviceMap.keySet().toArray(new String[serviceMap.size()]);
        return serviceMap.get(mkeys[rand]);
    }


}