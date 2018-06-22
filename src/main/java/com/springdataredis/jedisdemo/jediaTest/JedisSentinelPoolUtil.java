package com.springdataredis.jedisdemo.jediaTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 通过哨兵获得一个Master连接.
 */
public class JedisSentinelPoolUtil {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("127.0.0.1:63791");
        set.add("127.0.0.1:63792");
        //添加N个哨兵，当添加的时候，此时如果去看源码的话就会发现，顺带通过哨兵帮我们初始化了一个master连接地址
        JedisSentinelPool pool = new JedisSentinelPool("redis_master_name", set);
        //通过哨兵获得Master节点，如果有问题会重新通过哨兵获得一个Master节点
        Jedis jedis = pool.getResource();
        try {
            jedis.set("foot", "bar");
            String value = jedis.get("foot");
        } finally {
            //注意关闭
            jedis.close();
        }
    }

}
