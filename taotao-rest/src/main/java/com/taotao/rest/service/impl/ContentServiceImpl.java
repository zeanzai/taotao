package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.service.impl
 * @Date 2017/9/13 15:40
 * @Modified
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    /**
     *
     * @method getContentList
     * @param [contentCid]
     * @return java.util.List<com.taotao.pojo.TbContent>
     * @author w.x.y
     * @date 2017/9/15 16:05
     */
    @Override
    public List<TbContent> getContentList(long contentCid) {

        // 从缓存中查询内容信息，如果不为空就转化为list返回，如果为空就从数据库中去查询
        try {
            String result = jedisClient.hGet(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if (!StringUtils.isBlank(result)) {
                List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);

        // 向缓存中添加内容
        try {
            String cacheString = JsonUtils.objectToJson(list);
            jedisClient.hSet(INDEX_CONTENT_REDIS_KEY, contentCid+"", cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
