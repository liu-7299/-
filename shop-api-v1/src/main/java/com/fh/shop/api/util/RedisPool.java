package com.fh.shop.api.util;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Set;

public class RedisPool {

    private RedisPool(){

    }

    //private static JedisPool jedisPool = null;
    private static JedisCluster cluster = null;

    private static void init(){
        /*JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.217.128",7020);*/
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1000);
        // 最大空闲数
        poolConfig.setMaxIdle(20);
        // 最小空闲数
        poolConfig.setMinIdle(20);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(3000);
        Set<HostAndPort> nodes = new LinkedHashSet<>();
        nodes.add(new HostAndPort("192.168.217.128", 30001));
        nodes.add(new HostAndPort("192.168.217.128", 30002));
        nodes.add(new HostAndPort("192.168.217.128", 30003));
        nodes.add(new HostAndPort("192.168.217.128", 30004));
        nodes.add(new HostAndPort("192.168.217.128", 30005));
        nodes.add(new HostAndPort("192.168.217.128", 30006));
        cluster = new JedisCluster(nodes, poolConfig);
    }

    //静态代码块只会在加载类时执行一次
    static{
        init();
    }

    public static JedisCluster getJedis(){
        return cluster;
    }

    /*public static Jedis getJedis(){
        return jedisPool.getResource();
    }*/

}
