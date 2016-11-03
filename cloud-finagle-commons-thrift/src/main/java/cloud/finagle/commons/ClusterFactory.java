/**
 * @(#)ClusterFactory.java, 十一月 03, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.finagle.commons;

import com.google.common.collect.ImmutableSet;
import com.twitter.common.net.pool.DynamicHostSet;
import com.twitter.common.quantity.Amount;
import com.twitter.common.quantity.Time;
import com.twitter.common.zookeeper.ServerSet;
import com.twitter.common.zookeeper.ServerSetImpl;
import com.twitter.common.zookeeper.ZooKeeperClient;
import com.twitter.finagle.builder.Server;
import com.twitter.finagle.zookeeper.ZookeeperServerSetCluster;
import com.twitter.thrift.ServiceInstance;
import scala.collection.JavaConversions;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangpeng
 */
public class ClusterFactory {
    static Amount<Integer,Time> sessionTimeout = Amount.of(15, Time.SECONDS);
    static List<InetSocketAddress> nodes = Arrays.asList(
            // Use a cluster of nodes...
            // new InetSocketAddress("zk1.myapp.com", 2181),
            // new InetSocketAddress("zk2.myapp.com", 2181),
            // new InetSocketAddress("zk3.myapp.com", 2181),
            // new InetSocketAddress("zk4.myapp.com", 2181),
            // new InetSocketAddress("zk5.myapp.com", 2181),

            // ...or use local ZooKeeper node
            new InetSocketAddress("localhost", 2181)
    );

    public static ZookeeperServerSetCluster getForService(String clusterName) {
        ServerSet serverSet = new ServerSetImpl(getZooKeeperClient(), getPath(clusterName));
        return new ZookeeperServerSetCluster(serverSet);
    }

    public static void reportServerUpAndRunning(Server server, String clusterName) {
        getForService(clusterName).join(server.localAddress(), new scala.collection.immutable.HashMap());
    }

    public static List<SocketAddress> getOnlineServers(String clusterName) {
        try {
            ZookeeperServerSetCluster cluster = getForService(clusterName);
            // Run the monitor() method, which will block the thread until the initial list of servers arrives.
            new ServerSetImpl(zooKeeperClient, getPath(clusterName)).monitor(new DynamicHostSet.HostChangeMonitor<ServiceInstance>(){
                public void onChange(ImmutableSet<ServiceInstance> serviceInstances) {
                    // do nothing
                }
            });
            return JavaConversions.asJavaList(cluster.snap()._1());
        } catch (DynamicHostSet.MonitorException e) {
            throw new RuntimeException("Couldn't get list of online servers", e);
        }
    }

    private static String getPath(String clusterName) {
        return "/myapp/services/"+clusterName;
    }

    private static ZooKeeperClient zooKeeperClient;
    private static ZooKeeperClient getZooKeeperClient() {
        if (zooKeeperClient == null) {
            zooKeeperClient = new ZooKeeperClient(sessionTimeout, nodes);
        }
        return zooKeeperClient;
    }
}