package com.fh.shop.api.util;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class TestJedisPool {

    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.217.128", 30001));
        nodes.add(new HostAndPort("192.168.217.128", 30002));
        nodes.add(new HostAndPort("192.168.217.128", 30003));
        nodes.add(new HostAndPort("192.168.217.128", 30004));
        nodes.add(new HostAndPort("192.168.217.128", 30005));
        nodes.add(new HostAndPort("192.168.217.128", 30006));
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);
        cluster.set("aaa","1");
        String aaa = cluster.get("aaa");
        System.out.println(aaa);
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
