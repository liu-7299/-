package com.fh.shop.api.util;

import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.Map;

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

    public static Long del(String key){
        JedisCluster jedis = null;
        Long del = 0l;
        try {
            jedis = RedisPool.getJedis();
            del = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return del;
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

    public static Boolean exists(String key){
        JedisCluster jedis = null;
        Boolean exists = false;
        try {
            jedis = RedisPool.getJedis();
            exists = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return exists;
    }

    public static void hset(String key, String field, String value){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void hdel(String key, String... field){
        JedisCluster jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String hget(String key, String field){
        JedisCluster jedis = null;
        String value = "";
        try {
            jedis = RedisPool.getJedis();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return value;
    }

    public static Map<String, String> hgetall(String key){
        JedisCluster jedis = null;
        Map<String, String> map = new HashMap<>();
        try {
            jedis = RedisPool.getJedis();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return map;
    }

}
