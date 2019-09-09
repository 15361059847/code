package com.immoc.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收mq消息
 * Created by lenovo on 2019/9/2.
 */
@Slf4j
@Component
public class MqReceiver {

    //1、@RabbitListener(queues = "myQueue")
    //2、自动创建队列  @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3、自从创建，Exchange和Queue绑定。
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
            )
    )
    public void process(String message){
        log.info("MqReceiver：{}" + message);
    }


    /**
     * 数码供应商服务接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
        )
    )
    public void computer(String message){
        log.info("computerOrder MqReceiver：{}" + message);
    }


    /**
     * 水果供应商服务接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
        )
    )
    public void fruit(String message){
        log.info("fruit MqReceiver：{}" + message);
    }
}
