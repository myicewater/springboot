package com.jaxon.demo.redis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DistributeLock {

    //private static final String myLock = "MY_LOCK";

    private Jedis jedis;
    private static JedisPool jedisPool ;

    static {
        jedisPool = new JedisPool("192.168.5.106:6359:4");
    }

    public static JedisPool getPool(){
        if(jedisPool == null){
            jedisPool = new JedisPool();
        }
        return jedisPool;
    }

    public static String  lock(String lockName) {
        JedisPool jp = getPool();
       try(Jedis jedis = jp.getResource()){
           SetParams setParams = SetParams.setParams().nx().px(10);
           String uuid = UUID.randomUUID().toString();
           String value = jedis.set(lockName, uuid, setParams);
           if(value != null){
               return value;
           }
           return null;
       }
    }

    public static void unlock(String lockName,String value){
        JedisPool jp = getPool();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        try(Jedis jedis = jp.getResource()){
            Object result = jedis.eval(script, Collections.singletonList(lockName), Collections.singletonList(value));
            System.out.println(JSONObject.toJSONString(result));

        }
    }

    public static void close(){
        if(jedisPool != null){
            jedisPool.close();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long id = Thread.currentThread().getId();
                String value = lock("myLock");
                if (value != null) {
                    System.out.println(id+"获取到了myLock锁："+value);
                    Random random = new Random();
                    int i = random.nextInt(2000);
                    try{
                        Thread.sleep(i);
                        if(i>= 1000){
                            System.out.println("锁自动失效");
                        }else{

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(id+"获取到了myLock锁失败！");
                }



            }
        });

    }
}
