/**
 * @(#)zipkin.java, 十月 29, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
 * @author zhangpeng
 */
@SpringBootApplication
//@EnableZipkinStreamServer
@EnableZipkinServer
public class Zipkin {

    public static void main(String[] args) {
        SpringApplication.run(Zipkin.class, args);
    }
}