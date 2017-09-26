package com.taotao.rest.dao;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.dao
 * @Date 2017/9/15 10:57
 * @Modified
 */
public interface JedisClient {

    // 设值
    String set(String key, String value);

    // 取值
    String get(String key);

    // 设hash值
    Long hSet(String hKey, String key, String value);

    // 取hash值
    String hGet(String hKey, String key);

    // 自增1
    Long incr(String key);

    // 设值过期时间
    Long expire(String key, int second);

    // 获取过期状态
    Long ttl(String key); // -1：永久态 -2：过期状态 其他：剩余过期时间

    // 删除值
    Long del(String key);

    // 删除Hash值
    Long hDel(String hKey, String key);
}
