package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.portal.service
 * @Date 2017/9/25 16:00
 * @Modified
 */
public interface UserService {
    TbUser getUserByToken(String token);
}
