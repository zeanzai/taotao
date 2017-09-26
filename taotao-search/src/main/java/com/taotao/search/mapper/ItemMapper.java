package com.taotao.search.mapper;

import com.taotao.search.pojo.Item;

import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.search.mapper
 * @Date 2017/9/20 10:57
 * @Modified
 */
public interface ItemMapper {
    /*获取所有的商品列表*/
    List<Item> getItemList();
}
