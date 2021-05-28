package com.jaxon.demo.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class RedisDemo {

    public void singleReids(){
        while(true){


            try {
                JedisPool jedisPool = DistributeLock.getPool();
                Jedis jedis = jedisPool.getResource();

                jedis.set("hello","rredis");
                System.out.println(jedis.get("hello"));
                Thread.sleep(4000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
//        JedisPool jedisPool = new JedisPool("192.168.5.106",6379);

        Set<HostAndPort> hosts = new HashSet<HostAndPort>();
        hosts.add(new HostAndPort("192.168.3.201",6379));
        hosts.add(new HostAndPort("192.168.3.201",6380));
        hosts.add(new HostAndPort("192.168.3.201",6381));

        hosts.add(new HostAndPort("192.168.3.202",6379));
        hosts.add(new HostAndPort("192.168.3.202",6380));
        hosts.add(new HostAndPort("192.168.3.202",6381));

        JedisCluster cluster = new JedisCluster(hosts);
        cluster.set("clusterkey","cluuuuuuster");// 2 6

        while(true){

            try{
                Thread.sleep(4000);
                System.out.println(cluster.get("clusterkey"));

            }catch(Exception e){
                e.printStackTrace();
            }

        }



    }
}
