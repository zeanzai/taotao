package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.portal.controller
 * @Date 2017/9/11 15:22
 * @Modified
 */

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    /**
     * 展示门户首页
     * @method showIndex
     * @param [model]
     * @return java.lang.String
     * @author w.x.y
     * @date 2017/9/21 9:59
     */
    @RequestMapping("/index")
    public String showIndex(Model model) {
        String adJson = contentService.getContentList();
        model.addAttribute("ad1", adJson);
        return "index";
    }

    /**
     * @param
     * @return String
     * @method testPost
     * @author w.x.y
     * @date 2017/9/13 16:22
     */
    @RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
    @ResponseBody
    public String testPost() {
        return "OK";
    }

    /**
     * 测试带参数的post请求
     *
     * @param name
     * @param password
     * @return String
     * @method testPostWithParam
     * @author w.x.y
     * @date 2017/9/13 16:44
     */
    @RequestMapping(value = "/httpclient/testPostWithParam", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String testPostWithParam(String name, String password) {
        return "name: " + name + ", password: " + password;
    }
}
