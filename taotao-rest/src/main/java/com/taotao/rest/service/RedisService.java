package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 *
 * 用于同步数据库与Redis缓存的服务
 *
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.service
 * @Date 2017/9/15 17:16
 * @Modified
 */
public interface RedisService {
    TaotaoResult syncContent (Long contentCid);
}
