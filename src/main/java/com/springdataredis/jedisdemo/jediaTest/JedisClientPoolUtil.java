package com.springdataredis.jedisdemo.jediaTest;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.security.util.DerInputStream;

/**
 * 多线程环境下，线程池的正确使用方法，单例的连接池，单例的配置。
 * 此处给大家提供一个种思路，如果用spring boot的话，可以基于@Configuration 和@Bean的配置方法，此处仅仅是举例说明。
 */
//@Component
public class JedisClientPoolUtil {
//    @Value("{spring.redis.host}")
    private String host ="localhost";
//    @Value("{spring.redis.port}")
    private Integer port=6379;

    private final static byte[] temp_lock = new byte[1];

    private JedisPool jedisPool;

    /**
     * 设置连接池配置
     * @return
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(1000);
        return jedisPoolConfig;
    }

    /**
     * 把连接池做成单例的
     * @return
     */
    private JedisPool getJedisPool(){
        if (jedisPool == null) {
            synchronized (temp_lock) {
                jedisPool = new JedisPool(jedisPoolConfig(), host, port);
            }
        }
        return jedisPool;
    }

    /**
     * 对外只暴露一个方法即可
     * @return
     */
    public Jedis getJedis(){
        return getJedisPool().getResource();
    }

    public static void main(String[] args) {
//        @Autowired
//        JedisClientPoolUtil jedisClientPoolUtil;
//        如果在其他地方使用，直接Autowired即可。
        JedisClientPoolUtil jedisClientPoolUtil = new JedisClientPoolUtil();
        Jedis jedis = jedisClientPoolUtil.getJedis();
        try {
            jedis.set("foot", "bar");
            System.out.println(jedis.get("foot"));
        } finally {
           jedis.close();
        }
    }
}
