package com.ljx.message;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/12/20.
 */
public class Procuder {

    private static final Logger log = LoggerFactory.getLogger(Procuder.class);

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.252");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String,Object> heades = new HashMap<>();
        heades.put("my1","111");
        heades.put("my2","222");

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .headers(heades)
                .build();

        String msg = "Hello RabbitMQ";
        for(int i = 0; i < 5; i ++){
            log.info("生产端发送：{}", msg + i);
            channel.basicPublish("","test001", properties, (msg + i).getBytes());
        }
    }
}
