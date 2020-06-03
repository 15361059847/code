package com.ljx.returnlistener;

import com.ljx.exchange.quickstart.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by lenovo on 2019/12/26.
 */
public class RetuenCusumer {

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

        String exchange = "test_return_exchange";
        String routingKey = "return.#";
        String queueName = "test_return_queue";

        //声明了一个交换机,队列,绑定关系
        channel.exchangeDeclare(exchange, "topic", true,false,null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchange, routingKey);
        //使用自定义消费者
        channel.basicConsume(queueName, true, new MyConsumer(channel));
    }
}
