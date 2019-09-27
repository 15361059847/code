package com.immoc.controller;

import com.immoc.constant.CookieConstant;
import com.immoc.constant.RedisConstant;
import com.immoc.dataobject.UserInfo;
import com.immoc.enums.ResultEnum;
import com.immoc.enums.RoleEnum;
import com.immoc.service.UserService;
import com.immoc.utils.CookieUtil;
import com.immoc.utils.ResultVOUtil;
import com.immoc.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2019/9/24.
 */
@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    public  UserService userService;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;
    /**
     *  买家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVo buyer(@RequestParam("openid") String openid, HttpServletResponse response){
        //1、openid和数据库的数据进行匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if(userInfo ==null){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2、判断角色
        if(RoleEnum.BUYER.getCode() != userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR);
        }
        //3、cookie里设置的openid=abc
        CookieUtil.set(response, CookieConstant.OPENID,openid,CookieConstant.expire);
        return ResultVOUtil.success();
    }

    /**
     *  卖家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVo seller(@RequestParam("openid") String openid, HttpServletResponse response,HttpServletRequest request){
        //判断是否登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null && !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success();
        }
        //1、openid和数据库的数据进行匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if(userInfo ==null){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2、判断角色
        if(RoleEnum.SELLER.getCode() != userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR);
        }
        //3、redis设置key=UUID，value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                openid,expire, TimeUnit.SECONDS);

        //4、cookie里设置token
        CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.expire);
        return ResultVOUtil.success();
    }




}




