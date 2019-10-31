package com.fh.admin.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class RedisUtil {

    public static void set(String key,String value){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String get(String key){
        JedisCluster jedis = null;
        String value = null;
        try {
            jedis = RedisPool.getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return value;
    }

    public static void del(String key){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void del(String... key){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void expire(String key,int seconds){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setex(String key,String value,int seconds){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
