package com.immoc.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收mq消息
 * Created by lenovo on 2019/9/2.
 */
@Slf4j
@Component
public class MqReceiver {

    @RabbitListener(queues = "myQueue")
    public void process(String message){
        log.info("MqReceiver：{}" + message);
    }
}
