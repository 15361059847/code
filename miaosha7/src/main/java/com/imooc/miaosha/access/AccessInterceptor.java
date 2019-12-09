package com.imooc.miaosha.access;

import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.user.service.MiaoShaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2019/12/6.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            getMiaoShaUser(request,response);

            HandlerMethod hm =(HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
                return true;
            }
            accessLimit.seconds();
            accessLimit.maxCount();
            accessLimit.needLine();
        }

        return true;
    }

    private MiaoShaUser getMiaoShaUser(HttpServletRequest request,HttpServletResponse response){
        String paramToken = request.getParameter("token");
        String cookieToken = getCookieValue(request,"token");
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        MiaoShaUser user  = miaoShaUserService.getByToken(response,token);
        return user;
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies =  request.getCookies();
        if(cookies == null || cookies.length <=0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
