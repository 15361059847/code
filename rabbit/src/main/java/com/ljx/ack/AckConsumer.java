package com.ljx.ack;

import com.ljx.limit.QosConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by lenovo on 2019/12/26.
 */
public class AckConsumer {

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

        String exchange = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";

        //声明了一个交换机,队列,绑定关系
        channel.exchangeDeclare(exchange, "topic", true,false,null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchange, routingKey);

        //手工签收必须要 autoAck = false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
