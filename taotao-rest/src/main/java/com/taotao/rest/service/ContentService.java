package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.service
 * @Date 2017/9/13 15:39
 * @Modified
 */
public interface ContentService {
    List<TbContent> getContentList(long contentCid);
}
