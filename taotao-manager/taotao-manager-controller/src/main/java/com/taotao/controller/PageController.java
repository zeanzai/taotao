package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面controller
 * <p>Title: PageController</p>
 * <p>Description: </p>
 * <p>Company: http://www.posun.cn/</p>
 *
 * @author ShawnWang
 * @version 1.0
 * @date 2017年2月4日下午12:16:34
 */
@Controller
public class PageController {

    /*使用tomcat7插件，在编写程序时，不需要重启，直接就会更改到服务器上*/
    @RequestMapping("/")
    public String showIndexPage() {
        return "index"; /*需要将原来的index.jsp删除掉*/
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

}
