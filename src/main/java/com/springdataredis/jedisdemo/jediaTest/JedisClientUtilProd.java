package com.springdataredis.jedisdemo.jediaTest;

import redis.clients.jedis.Jedis;

/**
 * 单线程环境下使用，简单Util
 * 正常正式开发中，会把Jedis包装在一个单利模式中，避免每次都去重新连接，
 * 把localhost和port放到properties的配置文件中
 */
public class JedisClientUtilProd {
//    @Value("{spring.redis.host}")
    private String host ="localhost";

//    @Value("{spring.redis.port}")
    private Integer port=6379;

    private final byte[] temp_lock = new byte[1];

    private Jedis jedis;

    private JedisClientUtilProd(){}

    public Jedis getJedisClient (){
        if (jedis == null) {
            synchronized (temp_lock) {
                if (jedis == null) {
                    jedis = new Jedis(host,port);
                }
            }
        }
        return jedis;
    }

    public static void main(String[] args) {
//        @Autowired
//        JedisClientUtil JedisClientUtilProd;
//        如果在其他地方使用，直接Autowired即可。
        JedisClientUtilProd jedisClientUtilProd = new JedisClientUtilProd();
        Jedis jedis = jedisClientUtilProd.getJedisClient();
        try {
            jedis.set("foot", "barbar");
            String value = jedis.get("foot");
            System.out.println(value);
        } finally {
            //注意关闭
            jedis.close();
        }
    }
}
