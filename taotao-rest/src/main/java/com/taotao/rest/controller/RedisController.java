package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.controller
 * @Date 2017/9/15 17:24
 * @Modified
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public TaotaoResult contentSync(@PathVariable Long contentCid) {
        TaotaoResult taotaoResult = redisService.syncContent(contentCid);
        return taotaoResult;
    }
}
