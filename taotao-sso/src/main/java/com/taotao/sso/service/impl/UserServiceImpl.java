package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author w.x.y
 * @version V1.0
 * @Package com.taotao.sso.service.impl
 * @Date 2017/9/22 11:01
 * @Modified
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, Integer type) {

        //创建查询条件
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        //对数据进行校验：1、2、3分别代表username、phone、email
        //用户名校验
        if (1 == type) {
            criteria.andUsernameEqualTo(content);
            //电话校验
        } else if ( 2 == type) {
            criteria.andPhoneEqualTo(content);
            //email校验
        } else {
            criteria.andEmailEqualTo(content);
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list != null || list.size() > 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    /**
     * 用户注册
     * @method
     * @param 
     * @return 
     * @author w.x.y
     * @date 2017/9/22 11:36
     */
    @Override
    public TaotaoResult createUser(TbUser user) {
        user.setUpdated(new Date());
        user.setCreated(new Date());
        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample userExample = new TbUserExample();
        Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(userExample);
        if (null == list || list.size() < 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        //比对密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

        // 添加写Cookie的逻辑，cookie的有效期为关闭浏览器就失效
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);

        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        //根据token从redis中查询用户信息
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        //判断是否为空
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "此session已经过期，请重新登录");
        }
        //更新过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //返回用户信息
        return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
    }

    @Override
    public TaotaoResult logout(String token) {
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        //判断是否为空
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "此session已经自动过期，不用登出");
        }
        // 删除token
        jedisClient.del(REDIS_USER_SESSION_KEY + ":" +token);
        return TaotaoResult.build(200, "登出成功");
    }
}
