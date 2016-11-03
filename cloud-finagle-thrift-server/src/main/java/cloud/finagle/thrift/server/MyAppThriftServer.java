/**
 * @(#)MyAppThriftServer.java, 十一月 03, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.finagle.thrift.server;

import cloud.finagle.commons.ClusterFactory;
import com.example.myapp.thrift.FooService;
import com.twitter.finagle.builder.Server;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.stats.JavaLoggerStatsReceiver;
import com.twitter.finagle.thrift.ThriftServerFramedCodec;
import com.twitter.finagle.tracing.Tracer;
import com.twitter.ostrich.admin.AdminHttpService;
import com.twitter.ostrich.admin.RuntimeEnvironment;
import org.apache.thrift.protocol.TBinaryProtocol;
import zipkin.finagle.http.HttpZipkinTracer;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author zhangpeng
 */
public class MyAppThriftServer {
    public static void main(String[] args) {
        // In this example; use some random port between 2000-9999.
        // In a real world; probably deploy with the same port on different servers
        int port = new Random().nextInt(8000)+2000;

        System.setProperty("zipkin.http.host", "localhost:9411"); // default
        // Pass port number into our handler for this example (for debugging)
        FooServiceHandler handler = new FooServiceHandler("port="+port);

        HttpZipkinTracer.Config config = HttpZipkinTracer.Config.builder()
                                                                // The frontend makes a sampling decision (via Trace.letTracerAndId) and propagates it downstream.
                                                                // This property says sample 100% of traces.
                                                                .initialSampleRate(1.0f)
                                                                // All servers need to point to the same zipkin transport
                                                                .host("127.0.0.1:9411").build();

        Tracer tracer = HttpZipkinTracer.create(config,
                // print stats about zipkin to the console
                new JavaLoggerStatsReceiver(Logger.getAnonymousLogger()));

        Server server = ServerBuilder.safeBuild(
                new FooService.FinagledService(handler, new TBinaryProtocol.Factory()),
                ServerBuilder.get()
                             .name("FooService")
                             .codec(ThriftServerFramedCodec.get())
                             .tracer(tracer)
                             .maxConcurrentRequests(50)
                             // .logger(Logger.getLogger("ROOT"))
                             .bindTo(new InetSocketAddress(port))
        );

        ClusterFactory.reportServerUpAndRunning(server, "FooService");

        System.out.println("The server, running from port "+port+" joined the FooService cluster.");

        int ostrichPort = port + 1;
        RuntimeEnvironment runtime = new RuntimeEnvironment("");
        AdminHttpService admin = new AdminHttpService(ostrichPort, 0, runtime);
        admin.start();
        System.out.println("Ostrich reporting started on port "+ostrichPort);
    }
}
