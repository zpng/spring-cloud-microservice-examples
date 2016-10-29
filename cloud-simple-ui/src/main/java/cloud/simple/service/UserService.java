/**
 * @(#)UserService.java, 十月 28, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.service;

import cloud.simple.data.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangpeng
 */
@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;


    final String SERVICE_NAME = "cloud-simple-service";

    @HystrixCommand(fallbackMethod = "fallbackSearchAll")
    public List<User> readUserInfo() {
        return restTemplate.getForObject("http://" + SERVICE_NAME + "/user", List.class);
    }

    private List<User> fallbackSearchAll() {
        System.out.println("HystrixCommand fallbackMethod handle!");
        List<User> ls = new ArrayList<User>();
        User user = new User();
        user.setId(1);
        user.setUsername("TestHystrixCommand");
        ls.add(user);
        return ls;
    }
}