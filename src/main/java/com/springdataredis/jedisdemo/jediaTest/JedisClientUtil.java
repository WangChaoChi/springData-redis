package com.springdataredis.jedisdemo.jediaTest;

import redis.clients.jedis.Jedis;

/**
 * 单线程环境下使用，简单Util
 */
public class JedisClientUtil {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("foot", "bar");
        String value = jedis.get("foot");
        System.out.println(value);
        //通过这种方式就可以直接使用redis里面的很多命令了
    }
}
