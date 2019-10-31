package com.imooc.miaosha.good.controller;

import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.user.service.MiaoShaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lenovo on 2019/10/25.
 */
@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {


    @Autowired
    private GoodsService goodsService;


    @RequestMapping("/to_list")
    public String tolist(Model model, MiaoShaUser user) {
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo>  goodsList = goodsService.getGoodsVoList();
        model.addAttribute("goodsList",goodsList);
        return "goods_list";

    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoShaUser user, @PathVariable("goodsId") long goodsId) {


        return "goods_list";
    }

}
