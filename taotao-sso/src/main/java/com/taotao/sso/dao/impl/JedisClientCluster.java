package com.taotao.sso.dao.impl;

import com.taotao.sso.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.dao.impl
 * @Date 2017/9/15 11:18
 * @Modified
 */
public class JedisClientCluster implements JedisClient {
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public Long hSet(String hKey, String key, String value) {
        return jedisCluster.hset(hKey, key, value);
    }

    @Override
    public String hGet(String hKey, String key) {
        return jedisCluster.hget(hKey, key);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Long expire(String key, int second) {
        return jedisCluster.expire(key, second);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Long del(String key) {
        return jedisCluster.del(key);
    }

    @Override
    public Long hDel(String hKey, String key) {
        return jedisCluster.hdel(hKey, key);
    }
}
