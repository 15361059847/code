package com.ljx.exchange.quickstart;

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

    public static final String EXCHANGE_NAME = "test_consumer_exchange";
    public static final String EXCHANGE_TYPE = "topic";
    public static final String ROUTING_KEY_TYPE = "consumer.#";
    public static final String ROUTING_KEY = "consumer.save";
    public static final String QUEUE_NAME = "test_consumer_queue";

    public static void main(String[] args) throws Exception {
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
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

        //声明了一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true, false, null);
        //声明了一个队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //建立了一个绑定关系
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY_TYPE);

        //使用自定义消费者
        channel.basicConsume(QUEUE_NAME, true, new MyConsumer(channel));
        log.info("消费端启动成功");
    }

}
