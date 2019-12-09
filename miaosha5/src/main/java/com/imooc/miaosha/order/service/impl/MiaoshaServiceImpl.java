package com.imooc.miaosha.order.service.impl;

import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.order.model.MiaoshaOrder;
import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.order.service.MiaoshaService;
import com.imooc.miaosha.order.service.OrderService;
import com.imooc.miaosha.redis.service.RedisService;
import com.imooc.miaosha.redis.service.impl.MiaoShaKey;
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

    @Autowired(required = false)
    private RedisService redisService;


    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, GoodsVo goods) {
        //减库存、下订单、写入秒杀订单
        Boolean success = goodsService.reduceStock(goods);
        if(success){
            //减库存成功，往redis写一个标记。表示该用户已经秒杀该商品。
            setGoodsOver(goods.getId());
            return orderService.createOrder(user,goods);
        }
        return null;
    }

    @Override
    public long getMiaoshaResult(Long userid, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userid,goodsId);
        if(order != null){
            return order.getId();
        }else{
             boolean isOver = getGoodsOver(goodsId);
             if(isOver){
                return -1;
             }else {
                return 0   ;
             }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoShaKey.idGoodsOver,""+goodsId,true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoShaKey.idGoodsOver,""+goodsId);
    }


}
