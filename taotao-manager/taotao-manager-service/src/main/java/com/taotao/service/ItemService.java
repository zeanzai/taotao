package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

    TbItem geTbItemById(long itemId);

    EUDataGridResult getItemList(int page, int rows);

    TaotaoResult createItem(TbItem tbItem);

}
