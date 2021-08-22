package com.lp.rpc.registry;

import com.lp.rpc.constants.ZNodeType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZkOperation {

    private static ZooKeeper zk;

    private static ZkConnection zkConnection;

    final static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 创建节点
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void create(String path , byte[] data, int nodeType){
        zkConnection = new ZkConnection();
        zk = zkConnection.connection();
        try {
            if (nodeType == ZNodeType.PERSISTENT){
                zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            else if (nodeType == ZNodeType.PERSISTENT_SEQUENTIAL){
                zk.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            }
            else if (nodeType == ZNodeType.EPHEMERAL){
                zk.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
            else {
                zk.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


    /**
     * 判断Znode是否存在
     * @param path
     * @param watcher
     * @return
     */
    public static Stat exists(String path, boolean watcher){
        zkConnection = new ZkConnection();
        zk = zkConnection.connection();
        Stat b = null;
        try {
            b = zk.exists(path, watcher);
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 获取节点的附加信息
     * @param path
     * @return
     */
    public static String getData(String path){
        String datas = "";
        try {
            zkConnection = new ZkConnection();
            zk = zkConnection.connection();
            Stat stat = exists(path, false);
            if (stat != null){
                String finalDatas = datas;
                byte[] zkData = zk.getData(path, new Watcher() {
                    @SneakyThrows
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        if (watchedEvent.getType() == Event.EventType.None) {
                            switch (watchedEvent.getState()) {
                                case Expired:
                                    countDownLatch.countDown();
                                    break;
                            }
                        } else {
                            byte[] data = zk.getData(path, false, null);
                            String s = new String(data, "UTF-8");
                            log.info(s);
                            countDownLatch.countDown();
                        }
                    }
                }, null);
                String s = new String(zkData, "UTF-8");
                datas = s;
            }else {
                log.warn("ZNode does not exists");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return datas;
    }


    /**
     * 修改ZNode中附加数据
     * @param path
     * @param bytes
     * @param version
     * @return
     */
    public static Stat setData(String path , byte[] bytes , int version){
        zkConnection = new ZkConnection();
        zk = zkConnection.connection();
        Stat stat = null;
        try {
             stat = zk.setData(path, bytes,version);
        }catch (Exception e){
            e.printStackTrace();
        }
        return stat;
    }

    /**
     * 获取节点下子节点
     * @param path
     * @param watcher
     * @return
     */
    public static List<String> getChildren(String path , boolean watcher){
        zkConnection = new ZkConnection();
        zk = zkConnection.connection();
        List<String> children = null;
        try {
            children = zk.getChildren(path, watcher);
        }catch (Exception e){
            e.printStackTrace();
        }
        return children;
    }

    /**
     * 删除节点
     * @param path
     * @param version
     */
    public static void delete(String path , int version){
        zkConnection = new ZkConnection();
        zk = zkConnection.connection();
        if (exists(path,true) != null){
            if (getChildren(path, true).size() == 0){
                try {
                    zk.delete(path,version);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("此节点下存在子节点,请删除子节点后再删除当前节点");
            }
        }else {
            log.info("ZkNode does not exists");
        }
    }
}
