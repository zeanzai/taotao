package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;


/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: http://www.posun.cn/</p>
 *
 * @author ShawnWang
 * @version 1.0
 * @date 2017年2月4日上午10:09:15
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem geTbItemById(long itemId) {

        // 方法1：根据主键查询
        /*TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);*/

        // 方法2：使用TbItemExample添加查询条件
        TbItemExample tbItemExample = new TbItemExample();
        Criteria createCriteria = tbItemExample.createCriteria();
        createCriteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(tbItemExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();

        // 添加 商品状态=1 的条件
        /*Criteria createCriteria = example.createCriteria();
		createCriteria.andStatusEqualTo((byte) 1);*/

        //分页处理s
        PageHelper.startPage(page, rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem tbItem) {
        // TODO Auto-generated method stub

        tbItem.setId(IDUtils.genItemId());
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());

        itemMapper.insert(tbItem);

        return TaotaoResult.ok();
    }


}
