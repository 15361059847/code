package com.bfxy.springboot.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by lenovo on 2020/1/21.
 */
@Component
public class RabbitReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1",durable = "true"),
            exchange = @Exchange(value = "exchange-1",durable = "true",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws  Exception{
        System.out.println("-------------------------------");
        System.out.println("消费端Payload：" + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag,false);
    }

    /*  spring.rabbitmq.listener.order.queue.name=queue-2
        spring.rabbitmq.listener.order.queue.durable=true
        spring.rabbitmq.listener.order.exchange.name=exchange-2
        spring.rabbitmq.listener.order.exchange.durable=true
        spring.rabbitmq.listener.order.exchange.type=topic
        spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
        spring.rabbitmq.listener.order.key=springboot.**/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
            durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
            durable = "${spring.rabbitmq.listener.order.exchange.durable}",
            type = "${spring.rabbitmq.listener.order.exchange.type}",
            ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload com.bfxy.springboot.entity.Order order, Channel channel, @Headers Map<String,Object> headers) throws  Exception{
        System.out.println("-------------------------------");
        System.out.println("消费端order：" + order.getId());
        System.out.println("消费端order：" + order.getName());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag,false);
    }
}