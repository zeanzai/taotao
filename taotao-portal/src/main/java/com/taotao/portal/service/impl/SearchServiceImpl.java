package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.portal.service.impl
 * @Date 2017/9/21 15:03
 * @Modified
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, int page) {
        // 调用搜索服务
        // 查询参数
        Map<String, String> param = new HashMap<>();
        param.put("q", queryString);
        param.put("page", page+"");
        String url = SEARCH_BASE_URL + "/query";
        try {
            String json = HttpClientUtil.doGet(url, param);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
            if (taotaoResult.getStatus() == 200) {
                SearchResult result = (SearchResult) taotaoResult.getData();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
