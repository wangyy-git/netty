package com.wangyy.ltd.nettyapply.zookeeper.demo;

public class Zookeeper {
    public static void main(String[] args) throws Exception {
        BaseZookeeper zk = new BaseZookeeper();
        zk.connectZookeeper("192.168.195.108:2181");
        System.out.println(zk.createNode("/java", "java"));
    }
}
