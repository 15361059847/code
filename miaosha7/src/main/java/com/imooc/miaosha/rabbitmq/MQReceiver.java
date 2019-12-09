package com.imooc.miaosha.rabbitmq;


import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.order.model.MiaoshaOrder;
import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.order.service.MiaoshaService;
import com.imooc.miaosha.order.service.OrderService;
import com.imooc.miaosha.rabbitmq.model.MiaoshaMessage;
import com.imooc.miaosha.redis.service.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;


    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        String msg = redisService.beanToString(message);
        MiaoshaMessage mm = redisService.stringToBean(message, MiaoshaMessage.class);
        log.info("Receiver:" + mm.getMiaoShaUser());
        log.info("Receiver:" + mm.getGoodsId());
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(mm.getGoodsId());
        int count = goods.getStockCount();
        if(count <= 0){
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId( mm.getMiaoShaUser().getId(),mm.getGoodsId());
        if(order != null){
            return;
        }
        //减库存，下订单，写入秒杀订单
        miaoshaService.miaosha(mm.getMiaoShaUser(),goods);
    }


//    @RabbitListener(queues=MQConfig.QUEUE)
//    public void receive(String message){
//        String msg = redisService.beanToString(message);
//        log.info("Receiver:" + msg);
//    }
//
//    @RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message){
//        String msg = redisService.beanToString(message);
//        log.info("Receiver1:" + msg);
//    }
//
//    @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message){
//        String msg = redisService.beanToString(message);
//        log.info("Receiver2:" + msg);
//    }
//
//
//    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
//    public void receiveHeaderQueue(byte[] message) {
//        log.info(" header  queue message:"+new String(message));
//    }

}
