package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.search.service
 * @Date 2017/9/20 11:49
 * @Modified
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
