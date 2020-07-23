package com.jaxon.demo.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Map;

public class RedisWithReentrantlock {

    private ThreadLocal<Map> lockers = new ThreadLocal<Map>();
    private Jedis jedis;


    public RedisWithReentrantlock(Jedis jedis) {
        this.jedis = jedis;
    }

    private boolean lock(String key){
        SetParams setParams = new SetParams().nx().ex(5);
        jedis.set(key,"",setParams);
        return false;
    }

    private void unlock(String key) {
        jedis.del(key);
    }
}
