package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

public interface ContentCategoryService {
    
    /**
     *
     * @method getCategoryList
     * @param parentId
     * @return List<EUTreeNode>
     * @author w.x.y
     * @date 2017/9/11 10:50
     */
    List<EUTreeNode> getCategoryList(long parentId);
}
