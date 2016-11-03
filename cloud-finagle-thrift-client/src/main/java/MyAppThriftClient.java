/**
 * @(#)MyAppThriftClient.java, 十一月 03, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import cloud.finagle.commons.ClusterFactory;
import com.example.myapp.thrift.Foo;
import com.example.myapp.thrift.FooService;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.Cluster;
import com.twitter.finagle.stats.InMemoryStatsReceiver;
import com.twitter.finagle.stats.JavaLoggerStatsReceiver;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.finagle.tracing.Tracer;
import com.twitter.util.Duration;
import org.apache.thrift.protocol.TBinaryProtocol;
import zipkin.finagle.http.HttpZipkinTracer;

import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author zhangpeng
 */
public class MyAppThriftClient {
    public static void main(String[] args) {
        Cluster cluster = ClusterFactory.getForService("FooService");

        // Querying for a list of online servers is not necessary for Finagle,
        // but can be used for vanilla thrift servers.
        List<SocketAddress> onlineServers = ClusterFactory.getOnlineServers("FooService");
        System.out.println("Online servers: "+onlineServers.toString());

        // All servers need to point to the same zipkin transport
        System.setProperty("zipkin.http.host", "localhost:9411"); // default

        // It is unreliable to rely on implicit tracer config (Ex sometimes NullTracer is used).
        // Always set the tracer explicitly. The default constructor reads from system properties.

        HttpZipkinTracer.Config config = HttpZipkinTracer.Config.builder()
                                                                // The frontend makes a sampling decision (via Trace.letTracerAndId) and propagates it downstream.
                                                                // This property says sample 100% of traces.
                                                                .initialSampleRate(1.0f)
                                                                // All servers need to point to the same zipkin transport
                                                                .host("127.0.0.1:9411").build();

        Tracer tracer = HttpZipkinTracer.create(config,
                // print stats about zipkin to the console
                new JavaLoggerStatsReceiver(Logger.getAnonymousLogger()));

        Service<ThriftClientRequest, byte[]> service =
                ClientBuilder.safeBuild(ClientBuilder.get()
                                                     .cluster(cluster) // this is where service discovery happens
                                                     .name("FooService client")
                                                     .codec(ThriftClientFramedCodec.get())
                                                     .tracer(tracer)
                                                     .timeout(Duration.apply(2, TimeUnit.SECONDS))
                                                     .retries(4)
                                                     .hostConnectionLimit(1)
                        // .logger(Logger.getLogger("ROOT"))
                );

        FooService.FutureIface client = new FooService.FinagledClient(
                service,
                new TBinaryProtocol.Factory(),
                "FooService",
                new InMemoryStatsReceiver()
        );

        // Do some stuff
        for (int i = 0; i < 20; i++) {
            // Call .get() on the future to wait for it to return a value
            Foo foo = null;
            try {
                foo = client.giveMeSomeFoo(i).toJavaFuture().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("Got "+foo.getBazz());
            // (or use functional programming so you don't block the thread)
        }
    }
}