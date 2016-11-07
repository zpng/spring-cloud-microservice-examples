/**
 * @(#)zipkin.java, 十月 29, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * @author zhangpeng
 */
@SpringBootApplication
@EnableZipkinStreamServer
public class Zipkin {

    public static void main(String[] args) {
        SpringApplication.run(Zipkin.class, args);
    }
}