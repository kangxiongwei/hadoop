package com.kxw.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by kangxiongwei3 on 2017/4/9 20:55.
 */
public class ExampleZkClient {

    private static ZooKeeper zkClient;

    private static void init() throws IOException {
        String connectString = "10.180.153.155:2181";
        zkClient = new ZooKeeper(connectString, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    System.out.println(watchedEvent.getState() + "-" + watchedEvent.getPath());
                    zkClient.getChildren("/", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        init();
        //创建节点
        zkClient.create("/app", "hello zk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //判断是否存在
        Stat stat = zkClient.exists("/app", true);
        System.out.println(stat);
        //获取节点
        List<String> list = zkClient.getChildren("/", true);
        for (String key: list) {
            System.out.println(key);
        }
        //删除节点，-1表示删除所有的版本
        //zkClient.delete("/app10000000003", -1);
    }

}
