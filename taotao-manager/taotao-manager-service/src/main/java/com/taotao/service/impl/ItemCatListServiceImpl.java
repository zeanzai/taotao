package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatListService;

/**
 * 商品类目
 * <p>Title: ItemCatListServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: http://www.posun.cn/</p>
 *
 * @author ShawnWang
 * @version 1.0
 * @date 2017年2月6日下午2:29:48
 */
@Service
public class ItemCatListServiceImpl implements ItemCatListService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EUTreeNode> getCatList(long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(tbItemCatExample);
        List<EUTreeNode> euTreeNodes = new ArrayList<>();

        for (TbItemCat tbItemCat : tbItemCats) {
            EUTreeNode euTreeNode = new EUTreeNode();
            euTreeNode.setId(tbItemCat.getId());
            euTreeNode.setText(tbItemCat.getName());
            euTreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
            euTreeNodes.add(euTreeNode);
        }

        return euTreeNodes;
    }


}
