/**
 * @(#)SimpleService.java, 十月 28, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangpeng
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SimpleService {
    public static void main(String[] args) {
        SpringApplication.run(SimpleService.class, args);
    }
}