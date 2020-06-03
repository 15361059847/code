package com.ljx.dlx;

import com.ljx.ack.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/12/26.
 */
public class DlxConsumer {

    public static void main(String[] args)throws Exception{
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

        //这就是一个普通的交换机和队列以及路由
        String exchange = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        //声明了一个交换机,队列,绑定关系
        channel.exchangeDeclare(exchange, "topic", true,false,null);

        Map<String,Object> agruments = new HashMap<String,Object>();
        agruments.put("x-dead-letter-exchange","dlx.exchange");
        //这个agruments属性，要设置到声明队列上
        channel.queueDeclare(queueName, true, false, false, agruments);
        channel.queueBind(queueName, exchange, routingKey);

        //死信队列声明
        channel.exchangeDeclare("dlx.exchange", "topic", true,false,null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        //手工签收必须要 autoAck = false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
