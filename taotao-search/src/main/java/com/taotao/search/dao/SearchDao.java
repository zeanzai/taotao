package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.search.dao
 * @Date 2017/9/20 11:45
 * @Modified
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws Exception;
}
