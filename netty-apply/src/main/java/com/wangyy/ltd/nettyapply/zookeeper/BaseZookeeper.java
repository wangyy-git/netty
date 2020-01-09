package com.wangyy.ltd.nettyapply.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BaseZookeeper implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String IP = "192.168.195.108:2181";
    private static Integer TIME_OUT = 5000;
    private static ZooKeeper zk;
    public static void main(String[] args) {
        try {
            zk = new ZooKeeper(IP,TIME_OUT,new BaseZookeeper());
            countDownLatch.await();
            System.out.println("zk连接成功！");

            List<String> children = zk.getChildren("/", false);
            children.forEach(System.out::println);

//            BaseZookeeper baseZookeeper = new BaseZookeeper();
//            System.out.println(baseZookeeper.createNode("/java", "java"));
            createZnode();

            System.out.println("--------------------------------------");
            zk.create("/demo", "demo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            List<String> children2 = zk.getChildren("/", false);

            children2.forEach(System.out::println);
        
        } catch (IOException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            System.out.println("获取子节点失败");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("watcher 通知的内容 --> " + watchedEvent);
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
            System.out.println("watched received event ...");
            countDownLatch.countDown();
        }
        
    }

    private static void createZnode(){
        String path = "/java";
        byte[] data = "java".getBytes();
        try {
            String create = zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("create znode -> " + create);

        } catch (Exception e) {
            System.out.println("创建节点失败 ... ");
            e.printStackTrace();
        }
    }

    public String createNode(String path,String data) throws Exception{
        return this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void closeConnection() throws InterruptedException{

        if (zk != null) {
            zk.close();
        }
    }
}
