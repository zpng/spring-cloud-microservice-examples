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
import com.twitter.finagle.thrift.ThriftServerFramedCodec;
import com.twitter.ostrich.admin.AdminHttpService;
import com.twitter.ostrich.admin.RuntimeEnvironment;
import org.apache.thrift.protocol.TBinaryProtocol;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * @author zhangpeng
 */
public class MyAppThriftServer {
    public static void main(String[] args) {
        // In this example; use some random port between 2000-9999.
        // In a real world; probably deploy with the same port on different servers
        int port = new Random().nextInt(8000)+2000;

        // Pass port number into our handler for this example (for debugging)
        FooServiceHandler handler = new FooServiceHandler("port="+port);

        Server server = ServerBuilder.safeBuild(
                new FooService.FinagledService(handler, new TBinaryProtocol.Factory()),
                ServerBuilder.get()
                             .name("FooService")
                             .codec(ThriftServerFramedCodec.get())
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
