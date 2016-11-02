/**
 * @(#)ThriftConfig.java, 十一月 02, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.thrift.server.conf;

import cloud.thrift.UserService;
import cloud.thrift.server.service.UserServiceImpl;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangpeng
 */
@Configuration
public class ThriftConfig {


    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Bean
    public TServerTransport tServerTransport() {
        try {
            return new TServerSocket(7911);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public TServer tServer() {
        //发布服务
        UserService.Processor processor = new UserService.Processor(
                new UserServiceImpl());
        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
                tServerTransport()).processor(processor));
        return server;
    }

    @PostConstruct
    public void init(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tServer().serve();
            }
        });
    }

}