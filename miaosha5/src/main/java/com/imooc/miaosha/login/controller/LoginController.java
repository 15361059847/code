package com.imooc.miaosha.login.controller;

import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.login.vo.LoginVo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.user.service.MiaoShaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by lenovo on 2019/10/23.
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;


    @RequestMapping("/toLogin")
    public ModelAndView toLogin() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login.html");
        return mv;
    }

    @RequestMapping("/do_login")
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {
        log.info(loginVo.getMobile());
        //参数校验
        miaoShaUserService.login(response,loginVo);
        return Result.success(true);
    }

}
