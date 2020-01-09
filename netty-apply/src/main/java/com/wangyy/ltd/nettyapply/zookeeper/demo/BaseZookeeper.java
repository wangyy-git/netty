package com.wangyy.ltd.nettyapply.zookeeper.demo;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;



public class BaseZookeeper implements Watcher{
    private ZooKeeper zookeeper;
    /**
    * 超时时间
    */
    private static final int SESSION_TIME_OUT = 2000;
     private CountDownLatch countDownLatch = new CountDownLatch(1);
     @Override
     public void process(WatchedEvent event) {
     if (event.getState() == KeeperState.SyncConnected) {
     System.out.println("Watch received event");
     countDownLatch.countDown();
     }
     }
     
     
     
    
     /**连接zookeeper
      */
             public void connectZookeeper(String host) throws Exception{
     zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
     countDownLatch.await();
     System.out.println("zookeeper connection success");
     }
    
             /**
              * 创建节点
            */
             public String createNode(String path,String data) throws Exception{
     return this.zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
     }
    
     /**
      * 获取路径下所有子节点
      */
             public List<String> getChildren(String path) throws KeeperException, InterruptedException{
     List<String> children = zookeeper.getChildren(path, false);
     return children;
     }
    
             /**
      * 获取节点上面的数据
      */
             public String getData(String path) throws KeeperException, InterruptedException{
     byte[] data = zookeeper.getData(path, false, null);
     if (data == null) {
     return "";
     }
     return new String(data);
     }
    
             /**
      * 设置节点信息
      */
             public Stat setData(String path,String data) throws KeeperException, InterruptedException{
     Stat stat = zookeeper.setData(path, data.getBytes(), -1);
     return stat;
     }
    
    /**
      * 删除节点
      */
             public void deleteNode(String path) throws InterruptedException, KeeperException{
     zookeeper.delete(path, -1);
     }
    
             /**
      * 获取创建时间
      */
             public String getCTime(String path) throws KeeperException, InterruptedException{
     Stat stat = zookeeper.exists(path, false);
     return String.valueOf(stat.getCtime());
     }
    
             /**
      * 获取某个路径下孩子的数量
      */
             public Integer getChildrenNum(String path) throws KeeperException, InterruptedException{
     int childenNum = zookeeper.getChildren(path, false).size();
     return childenNum;
     }
     /**
      * 关闭连接
      */
    public void closeConnection() throws InterruptedException{
        
        if (zookeeper != null) {
            zookeeper.close();
        }
     }

}
 
 
 
