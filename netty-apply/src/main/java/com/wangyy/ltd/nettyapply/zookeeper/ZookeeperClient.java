package com.wangyy.ltd.nettyapply.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ZookeeperClient implements Watcher {
    private static ZooKeeper zk;
    public static void main(String[] args) throws Exception {
        //集群是将IP:PORT,拼接在一起
//        String zkServer = "192.168.195.108:2181,192.168.195.108:2182,192.168.195.108:2183";
        String zkServer = "192.168.195.108:2181";
        Integer timeOut = 5000;
        zk = new ZooKeeper(zkServer,timeOut,new ZookeeperClient());
        System.out.println("连接到zkServer --> " + zk);
        Thread.sleep(1000);
        System.out.println("连接状态 --> " + zk.getState());

        //sessionId  sessionPasswd用于恢复zk会话
        long sessionId = zk.getSessionId();
        System.out.println("sessionId ->" + sessionId);

        byte[] sessionPasswd = zk.getSessionPasswd();
        System.out.print("sessionPasswd --> ");
        Arrays.asList(sessionPasswd).forEach(System.out::println);

        Thread.sleep(1000);
        System.out.println("获取 --> " + zk.getChildren("/",false).toString());
        
        createZnode();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("watcher 通知的内容 --> " + watchedEvent);
    }

    /**
     * path:创建的路径
     * data:数组的byte[]
     * acl:控制权限策略   
     *      Ids.ANYONE_ID_UNSAFE -> word:anyone:cdrwa
     *      Ids.CREATOR_ALL_ACL -> auth:user:password:cdrwa
     * CreateMode:节点类型，是一个枚举
     *      PERSISTENT -- 持久节点
     *      PERSISTENT_SEQUENTIAL -- 持久顺序节点
     *      EPHEMERAL -- 临时节点
     *      EPHEMERAL_SEQUENTIAL -- 临时顺序节点
     * 
     */
    private static void createZnode(){
        String path = "/java";
        byte[] data = "java".getBytes();
        try {
            String create = zk.create(path, data, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
            System.out.println("create znode -> " + create);
            
        } catch (Exception e) {
            System.out.println("创建节点失败 ... ");
            e.printStackTrace();
        } 
    }
}
