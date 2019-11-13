package com.imooc.miaosha.user.service.impl;

import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.login.vo.LoginVo;
import com.imooc.miaosha.redis.service.RedisService;
import com.imooc.miaosha.redis.service.impl.MiaoshaUserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.user.dao.MiaoShaUserDao;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.user.service.MiaoShaUserService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("MiaoShaUserService")
public class MiaoShaUserServiceImpl implements MiaoShaUserService {

    private static final String COOKIE_NAME_TOKEN = "token";

    @Autowired(required = false)
    private MiaoShaUserDao miaoShaUserDao;

    @Autowired(required = false)
    private RedisService redisService;



    public MiaoShaUser getById(long id){
      return  miaoShaUserDao.getById(id);
    }

    @Override
    public boolean login(HttpServletResponse response ,LoginVo loginVo) {
        if(loginVo == null){
            throw new GlobalException( CodeMsg.SERVER_ERROR);
        }
        String formPass = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        //检查用户是否存在
        MiaoShaUser user =  getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException( CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie//生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,user,token);
        return true;
    }

    @Override
    public MiaoShaUser getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoShaUser user = redisService.get(MiaoshaUserKey.token,token, MiaoShaUser.class);
        //设置缓冲中的token，重新设置cookie。达到延长有效期的目的
        if(user != null){
            addCookie(response,user,token);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response,MiaoShaUser miaoShaUser,String token){
        redisService.set(MiaoshaUserKey.token,token,miaoShaUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
