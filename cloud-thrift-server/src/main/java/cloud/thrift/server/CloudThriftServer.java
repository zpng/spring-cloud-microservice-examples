/**
 * @(#)CloudThriftServer.java, 十一月 02, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.thrift.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangpeng
 */
@SpringBootApplication
public class CloudThriftServer{

    public static void main(String[] args) {
        SpringApplication.run(CloudThriftServer.class, args);
    }
}