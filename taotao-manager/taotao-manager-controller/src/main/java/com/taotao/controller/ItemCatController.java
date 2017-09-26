package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类controller
 * <p>Title: ItemCatController</p>
 * <p>Description: </p>
 * <p>Company: http://www.posun.cn/</p>
 *
 * @author ShawnWang
 * @version 1.0
 * @date 2017年2月6日下午3:31:22
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatListService itemCatListService;

    /**
     * 选择类目时的加载函数
     * 通过id获取节点，在初始化过程中要给一个默认值；
     * 此处函数的 访问域 是private 和 public 没有关系，哪一个都可以
     * <p>Title: list</p>
     * <p>Description: </p>
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    private List<EUTreeNode> list(@RequestParam(value = "id", defaultValue = "0") long parentId) {
        List<EUTreeNode> catList = itemCatListService.getCatList(parentId);
        return catList;

    }

}
