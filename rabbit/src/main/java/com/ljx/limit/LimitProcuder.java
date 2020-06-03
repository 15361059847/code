package com.ljx.limit;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lenovo on 2019/12/26.
 */
public class LimitProcuder {


    private static final Logger log = LoggerFactory.getLogger(LimitProcuder.class);

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

        String exchange = "test_qos_exchange";
        String routingKey = "qos.save";

        String msg = "Hello RabbitMQ Send qos message!";

        for(int i = 0;i<5;i++) {
            channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
        }
    }
}
