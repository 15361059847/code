package com.ljx.exchange.fanout;

import com.ljx.exchange.quickstart.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer4FanoutExchange {

	public static void main(String[] args) throws Exception {
		
        ConnectionFactory connectionFactory = new ConnectionFactory() ;

        connectionFactory.setHost("192.168.1.252");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
		
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        
        Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_fanout_exchange";
		String exchangeType = "fanout";
		String queueName = "test_fanout_queue";
		String routingKey = "";	//不设置路由键
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

        //使用自定义消费者
        channel.basicConsume(queueName, true, new MyConsumer(channel));
	}
}
