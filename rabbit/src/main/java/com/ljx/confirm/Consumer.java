package com.ljx.confirm;

import com.ljx.exchange.quickstart.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by lenovo on 2019/12/20.
 */
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);



    public static void main(String[] args) throws Exception {
        //创建connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置属性
        connectionFactory.setHost("192.168.1.252");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        //是否自动重连
        connectionFactory.setAutomaticRecoveryEnabled(true);
        //每3秒钟重连一次
        connectionFactory.setNetworkRecoveryInterval(3000);

        //2 获取C onnection
        Connection connection = connectionFactory.newConnection();
        //3 通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.#";
        String queueName = "test_confirm_queue";

        //声明了一个交换机,队列,绑定关系
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        //使用自定义消费者
        channel.basicConsume(queueName, true, new MyConsumer(channel));


        log.info("消费端启动成功");
    }

}
