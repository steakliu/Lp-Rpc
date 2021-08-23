package com.lp.rpc.registry;

import com.lp.rpc.constants.LpConstant;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZkConnection {
    private static ZooKeeper zoo;
    final static CountDownLatch connectionSign = new CountDownLatch(1);

    String ip = "116.198.160.39";
    String addrs = ip + ":2181," + ip + ":2182," + ip + ":2183";
    public ZooKeeper connection(){
        try {
            zoo = new ZooKeeper(addrs, LpConstant.zkTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
                        connectionSign.countDown();
                    }
                }
            });
            connectionSign.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        return zoo;
    }

    public void close() throws InterruptedException {
        zoo.close();
    }
}
