package com.ljx.exchange.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        String msg = "Hello RabbitMQ Consumer Message";
        for(int i = 0; i < 5; i ++){
            log.info("生产端发送：{}", msg + i);
            channel.basicPublish(Consumer.EXCHANGE_NAME, Consumer.ROUTING_KEY, true, null, (msg + i).getBytes());
        }
    }
}
