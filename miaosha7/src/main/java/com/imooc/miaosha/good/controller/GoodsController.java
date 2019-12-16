package com.imooc.miaosha.good.controller;

import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.redis.service.RedisService;
import com.imooc.miaosha.redis.service.impl.GoodsKey;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.vo.GoodsDetailVo;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lenovo on 2019/10/25.
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    /**
     * 视图解析器
     */
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品菜单列表
     */
    @RequestMapping(value = "/to_list",produces="text/html;charset=utf-8")
    public String tolist(HttpServletRequest request, HttpServletResponse response, Model model, MiaoShaUser user) {
        if (user == null) {
            return "没有登录";
        }
        model.addAttribute("user",user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //查询商品列表
        List<GoodsVo>  goodsList = goodsService.getGoodsVoList();
        model.addAttribute("goodsList",goodsList);
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());

        //手动渲染
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value ="/to_detail2/{goodsId}",produces="text/html;charset=utf-8")
    public String toDetail2(HttpServletRequest request, HttpServletResponse response,Model model, MiaoShaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail,""+goodsId,String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if(now < startAt){//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now)/1000;
        }else if(now > endAt){//秒杀结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }

        return html;
    }


    @RequestMapping(value ="/detail/{goodsId}")
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoShaUser user, @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt){//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now)/1000;
        }else if(now > endAt){//秒杀结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }


}
