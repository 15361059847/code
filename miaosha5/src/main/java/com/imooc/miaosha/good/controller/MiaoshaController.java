package com.imooc.miaosha.good.controller;

import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.order.model.MiaoshaOrder;
import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.order.service.MiaoshaService;
import com.imooc.miaosha.order.service.OrderService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lenovo on 2019/10/25.
 */
@Controller
@RequestMapping("/miaosha")
@Slf4j
public class MiaoshaController {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;


    @Autowired
    private MiaoshaService miaoshaService;

    /**
     * GTE跟POST区别
     * GET幂等
     */
    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> doMiaosha(Model model, MiaoShaUser user,
                            @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user",user);
        if(user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        //判断库存
        GoodsVo  goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int count = goods.getStockCount();
        if(count <= 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        return Result.success(orderInfo);

    }


}
