package com.jaxon.demo.zk;

import com.alibaba.fastjson.JSONObject;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZkUtil {

    private static final String ZK_URL = "zk.goo.com:2181";

    private static int sessionTimeout = 5000;

    private ZooKeeper zookeeper = null;

    public ZooKeeper  getZk()  {
        if(zookeeper != null){
            return zookeeper;
        }

        try {
            CountDownLatch countDown = new CountDownLatch(1);
            zookeeper = new ZooKeeper(ZK_URL,sessionTimeout,new Watcher(){

                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        System.err.println("eventType:"+event.getType());
                        if(event.getType()==Event.EventType.None){
                            countDown.countDown();
                        }else if(event.getType()==Event.EventType.NodeCreated){
                            System.out.println("listen:节点创建");
                        }else if(event.getType()==Event.EventType.NodeChildrenChanged){
                            System.out.println("listen:子节点修改");
                        }
                    }
                }
            });

            countDown.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zookeeper;
    }


    public String createNode(String path,String content,CreateMode mode){
        ZooKeeper zookeeper = getZk();
        String s = null;
        try {
            s = zookeeper.create(path, content.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void test1() throws KeeperException, InterruptedException {
        ZkUtil zkutil  = new ZkUtil();
        ZooKeeper zk = zkutil.getZk();
        //+ "192.168.101.60"

        if(zk.exists("/digitalreceipt" , false) == null){
            zk.create("/digitalreceipt", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        if(zk.exists("/digitalreceipt/deviceconnect" , false) == null){
            String s = zk.create("/digitalreceipt/deviceconnect", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(s);
        }

        if(zk.exists("/digitalreceipt/deviceserver" , false) == null){
            String s = zk.create("/digitalreceipt/deviceserver", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(s);
        }

        if(zk.exists("/digitalreceipt/deviceserver"+"/192.168.101.60" , false) != null){//临时节点存在则删除调
            zk.delete("/digitalreceipt/deviceserver"+"/192.168.101.60",0);
        }
        String s = zk.create("/digitalreceipt/deviceserver" + "/192.168.101.60", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(s);
        Thread.sleep(10000);
    }


    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        CountDownLatch countDown = new CountDownLatch(1);
        ZooKeeper zookeeper = new ZooKeeper(ZK_URL,sessionTimeout,new Watcher(){

            @Override
            public void process(WatchedEvent event) {//检测连接 断开事件
                System.out.println("Keep watch:"+event.getState());
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.err.println("eventType:"+event.getType());
                    if(event.getType()==Event.EventType.None){
                        countDown.countDown();
                    }else if(event.getType()==Event.EventType.NodeCreated){
                        System.out.println("listen:节点创建");
                    }else if(event.getType()==Event.EventType.NodeChildrenChanged){
                        System.out.println("listen:子节点修改");
                    }
                }
            }
        });

        countDown.await();

        String s = zookeeper.create("/linsssshi" , "haha".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("结果"+s);

        while(true){


        }
    }

}
