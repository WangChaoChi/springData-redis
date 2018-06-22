package com.springdataredis.jedisdemo.jediaTest;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class ShardedJedisPoolUtil {
    public static void main(String[] args) {
        List<JedisShardInfo> shardInfos = new ArrayList<>();
        shardInfos.add(new JedisShardInfo("127.0.0.1", 6379));
        shardInfos.add(new JedisShardInfo("127.0.0.1", 6378));

        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(new GenericObjectPoolConfig(), shardInfos);
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        shardedJedis.set("key1", "abc");
        System.out.println(shardedJedis.get("key1"));
    }
}
