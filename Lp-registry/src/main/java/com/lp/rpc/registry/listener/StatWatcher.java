package com.lp.rpc.registry.listener;

import lombok.AllArgsConstructor;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.Arrays;
@AllArgsConstructor
public class StatWatcher implements Watcher{

    private ZooKeeper zooKeeper;



    @Override
    public void process(WatchedEvent watchedEvent) {
        byte[] bytes = null;
        try {
            bytes = zooKeeper.getData("/service-provider-1",true,null);
        }catch (Exception e){
            throw new RuntimeException("zk exception");
        }
        System.out.println("listener----------  "+ Arrays.toString(bytes));
    }
}
