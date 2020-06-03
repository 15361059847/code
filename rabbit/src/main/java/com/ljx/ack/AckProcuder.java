package com.ljx.ack;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/12/26.
 */
public class AckProcuder {


    private static final Logger log = LoggerFactory.getLogger(AckProcuder.class);

    public static void main(String[] args) throws Exception{
        //创建connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置属性
        connectionFactory.setHost("192.168.1.252");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        //获取connection
        Connection connection = connectionFactory.newConnection();
        //通过connection创建channel
        Channel channel = connection.createChannel();

        String exchange = "test_ack_exchange";
        String routingKey = "ack.save";

        for(int i = 0;i<5;i++) {
            Map<String,Object> heades = new HashMap<>();
            heades.put("num",i);

            AMQP.BasicProperties basicProperties = new   AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(heades)
                    .build();
            String msg = "Hello RabbitMQ Send ACK message!" + i;
            channel.basicPublish(exchange, routingKey, true, basicProperties, msg.getBytes());
        }
    }
}
