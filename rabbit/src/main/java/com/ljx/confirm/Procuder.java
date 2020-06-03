package com.ljx.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lenovo on 2019/12/20.
 */
public class Procuder {

    private static final Logger log = LoggerFactory.getLogger(Procuder.class);

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

        //指定消息的投递模式，消息的确认模式。
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        String msg = "Hello RabbitMQ Send confirm message!!";
        channel.basicPublish(exchangeName, routingKey,null,msg.getBytes());

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //回调成功进入的方法
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                log.info("------------------- ack");
            }
            //回调失败进入的方法
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                log.info("-------------------no ack");
            }
        });

    }
}
