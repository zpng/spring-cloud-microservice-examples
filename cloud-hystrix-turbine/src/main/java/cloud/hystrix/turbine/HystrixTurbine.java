/**
 * @(#)HystrixTurbine.java, 十月 31, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author zhangpeng
 */

@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableTurbine
@EnableDiscoveryClient
public class HystrixTurbine {

    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbine.class, args);
    }
}
