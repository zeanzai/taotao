package com.taotao.rest.dao.impl;

import com.taotao.rest.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.dao.impl
 * @Date 2017/9/15 11:03
 * @Modified
 */
public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String set(String key, String value) {
        Jedis resource = jedisPool.getResource();
        String s = resource.set(key, value);
        resource.close();
        return s;
    }

    @Override
    public String get(String key) {
        Jedis resource = jedisPool.getResource();
        String s = resource.get(key);
        resource.close();
        return s;
    }

    @Override
    public Long hSet(String hKey, String key, String value) {
        Jedis resource = jedisPool.getResource();
        Long l = resource.hset(hKey, key, value);
        resource.close();
        return l;
    }

    @Override
    public String hGet(String hKey, String key) {
        Jedis resource = jedisPool.getResource();
        String s = resource.hget(hKey, key);
        resource.close();
        return s;
    }

    @Override
    public Long incr(String key) {
        Jedis resource = jedisPool.getResource();
        Long i = resource.incr(key);
        resource.close();
        return i;
    }

    @Override
    public Long expire(String key, int second) {
        Jedis resource = jedisPool.getResource();
        Long ep= resource.expire(key, second);
        resource.close();
        return ep;
    }

    @Override
    public Long ttl(String key) {
        Jedis resource = jedisPool.getResource();
        Long t = resource.ttl(key);
        resource.close();
        return t;
    }

    @Override
    public Long del(String key) {
        Jedis resource = jedisPool.getResource();
        Long d = resource.del(key);
        resource.close();
        return d;
    }

    @Override
    public Long hDel(String hKey, String key) {
        Jedis resource = jedisPool.getResource();
        Long d = resource.hdel(hKey, key);
        resource.close();
        return d;
    }
}
