package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.rest.service.impl
 * @Date 2017/9/11 16:28
 * @Modified
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    private List<?> getCatList(long parentId) {
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //返回值list
        List resultList = new ArrayList<>();
        //向list中添加节点
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/"+tbItemCat.getId()+".html");
                catNode.setItem(getCatList(tbItemCat.getId()));

                resultList.add(catNode);
                count ++;
                //第一层只取14条记录
                if (parentId == 0 && count >=14) {
                    break;
                }
                //如果是叶子节点
            } else {
                resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
            }
        }
        return resultList;
    }

    @Override
    public CatResult getItemCatList() {

        CatResult catResult = new CatResult();

        // 从缓存中获取商品类目信息
        /*try {
            String result = jedisClient.hGet(INDEX_CONTENT_REDIS_KEY, 0 + "");
            if (!StringUtils.isBlank(result)) {
                CatResult catResultRedis = JsonUtils.jsonToPojo(result, CatResult.class);
                return catResultRedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //如果缓存中查询结果为空，则从数据库中查询分类列表
        catResult.setData(this.getCatList(0));

        // 将从数据库中查询的结果放到缓存中去
        /*try {
            String cacheString = JsonUtils.objectToJson(this.getCatList(0));
            jedisClient.hSet(INDEX_CONTENT_REDIS_KEY, 0+"", cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return catResult;
    }
}
