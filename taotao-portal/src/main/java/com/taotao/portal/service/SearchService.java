package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * 搜索服务
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.portal.service
 * @Date 2017/9/21 15:01
 * @Modified
 */
public interface SearchService {
    SearchResult search(String queryString, int page);
}
