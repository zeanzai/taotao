package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.sso.controller
 * @Date 2017/9/22 11:04
 * @Modified
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*用户数据校验*/
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {

        TaotaoResult result = null;

        //参数有效性校验
        if (StringUtils.isBlank(param)) {
            result = TaotaoResult.build(400, "校验内容不能为空");
        }
        if (type == null) {
            result = TaotaoResult.build(400, "校验内容类型不能为空");
        }
        if (type != 1 && type != 2 && type != 3 ) {
            result = TaotaoResult.build(400, "校验内容类型错误");
        }
        //校验出错
        if (null != result) {
            if (null != callback) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }
        }

        //调用服务
        try {
            result = userService.checkData(param, type);

        } catch (Exception e) {
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return result;

    }

    /*用户注册*/
    @RequestMapping(value = "/register", method = RequestMethod.POST) /*如果不加POST，那么POST和get都会相应*/
    @ResponseBody
    public TaotaoResult createUser(TbUser user) {

        try {
            TaotaoResult result = userService.createUser(user);
            return result;
        } catch (Exception e) {
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /*用户登录*/
    @RequestMapping(value="/login", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            TaotaoResult result = userService.userLogin(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        //判断是否为jsonp调用
        if (StringUtils.isBlank(callback)) {
            return result;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

    @RequestMapping("/logout/{token}")
    @ResponseBody
    public TaotaoResult logout(@PathVariable String token) {
        TaotaoResult logoutResult = userService.logout(token);
        return logoutResult;
    }

}
