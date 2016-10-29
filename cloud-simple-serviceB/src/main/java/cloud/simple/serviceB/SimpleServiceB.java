/**
 * @(#)SimpleServiceB.java, 十月 29, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.serviceB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangpeng
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SimpleServiceB {
    public static void main(String[] args) {
        SpringApplication.run(SimpleServiceB.class, args);
    }
}