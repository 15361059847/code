package com.ljx.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by lenovo on 2019/12/26.
 */
public class LimitConsumer {

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

        String exchange = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";

        //声明了一个交换机,队列,绑定关系
        channel.exchangeDeclare(exchange, "topic", true,false,null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchange, routingKey);

        //1、限流方式，第一件事就是autoAck设置为false，手动签收
        channel.basicQos(0,1,false);

        channel.basicConsume(queueName, false, new QosConsumer(channel));
    }
}
