package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.sso.service
 * @Date 2017/9/22 11:00
 * @Modified
 */
public interface UserService {
    TaotaoResult checkData(String content, Integer type);
    TaotaoResult createUser(TbUser user);
    TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult getUserByToken(String token);
    TaotaoResult logout(String token);
}
