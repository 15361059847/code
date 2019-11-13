package com.imooc.miaosha.order.service.impl;

import com.imooc.miaosha.good.model.Goods;
import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.order.dao.OrderDao;
import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.order.service.MiaoshaService;
import com.imooc.miaosha.order.service.OrderService;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2019/11/1.
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired(required = false)
    private GoodsService goodsService;

    @Autowired(required = false)
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, GoodsVo goods) {
        //减库存、下订单、写入秒杀订单
        goodsService.reduceStock(goods);
        return orderService.createOrder(user,goods);
    }
}
