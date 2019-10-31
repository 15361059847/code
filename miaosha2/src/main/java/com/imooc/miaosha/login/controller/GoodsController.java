package com.imooc.miaosha.login.controller;

import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.user.model.User;
import com.imooc.miaosha.user.service.MiaoShaUserService;
import com.imooc.miaosha.user.service.impl.MiaoShaUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2019/10/25.
 */
@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;


    @RequestMapping("/to_list")
    public String tolist(Model model, MiaoShaUser user) {
        if(user == null){
            return "login";
        }
        model.addAttribute("user",user);
        return "goods_list";

    }










//    @RequestMapping("/to_detail")
//    public String toDetail(HttpServletResponse response,Model model,
//                         @CookieValue(value = "token",required = false) String cookieToken,
//                         @RequestParam(value = "token",required = false) String paramToken) {
//
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoShaUser u  = miaoShaUserService.getByToken(response,token);
//        return "goods_list";
//
//    }

}
