package com.lp.rpc.registry.listener;

import com.lp.rpc.registry.ZkConnection;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.Arrays;

/**
 * 注册中心监控
 */
public class ZkRegistryListener{

    private ZooKeeper zooKeeper;

    String ip = "116.198.160.39";
    String addrs = ip + ":2181," + ip + ":2182," + ip + ":2183";

    private void listener(){
        try {
            zooKeeper = new ZooKeeper(addrs,5000, new StatWatcher(zooKeeper));
        }catch (Exception e){
            throw new RuntimeException("zk listener exception");
        }
    }

//    public static void main(String[] args) {
//        //初始化log4j，zookeeper否则报错。
//        org.apache.log4j.BasicConfigurator.configure();
//
//        try {
//            ZkRegistryListener m = new ZkRegistryListener("");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public ZkRegistryListener(String arg){
//        try {
//            test();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private ZooKeeper mZooKeeper;
//
//    private void test() throws Exception{
//        String ip = "116.198.160.39";
//        String addrs = ip + ":2181," + ip + ":2182," + ip + ":2183";
//
//        //连接zookeeper服务器。
//        //addrs是一批地址，如果其中某一个服务器挂掉，其他仍可用。
//        mZooKeeper = new ZooKeeper(addrs, 300 * 1000, new MyWatcher());
//
//        synchronized (ZkRegistryListener.class) {
//            System.out.println("处理...");
//            ZkRegistryListener.class.wait();
//        }
//    }
//
//    private class MyWatcher implements Watcher {
//        @Override
//        public void process(WatchedEvent event) {
//            byte[] data =null;
//            try {
//                data = mZooKeeper.getData("/service-provider-1/com.lp.rpc.service.UserService", true, null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("类型:" + event.getType());
//            System.out.println("路径:" + event.getPath());
//            if(data!=null)
//                System.out.println("数据修改:" + new String(data));
//
//            if (mZooKeeper.getState() == ZooKeeper.States.CONNECTED) {
//                System.out.println("状态:ZooKeeper.States.CONNECTED");
//            }
//
//            if (event.getState() == Event.KeeperState.SyncConnected) {
//                System.out.println("状态:Event.KeeperState.SyncConnected");
//            }
//        }
//    }
}
