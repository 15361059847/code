package com.bfxy.springboot.producer;

import com.bfxy.springboot.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.MethodWrapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lenovo on 2020/1/21.
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData : " + correlationData);
            System.out.println("ack : " + ack);
            if(!ack){
                System.out.println("异常处理....");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            System.out.println("return  exchange: " + exchange);
            System.out.println("return  routingKey: " + routingKey);
            System.out.println("return  replyCode: " + replyCode);
            System.out.println("return  replyText: " + replyText);
            System.out.println("return  message: " + message.getBody());
        }
    };


    public void send(Object message, Map<String,Object> properties) throws  Exception{
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message,mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("123456789");//id+ 时间戳 全局唯一
        rabbitTemplate.convertAndSend("exchange-1","springboot.hello",msg,correlationData);

    }

    public void sendOrder(Order order) throws  Exception{
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData("0987654321");//id+ 时间戳 全局唯一
        rabbitTemplate.convertAndSend("exchange-2","springboot.def",order,correlationData);

    }
}
