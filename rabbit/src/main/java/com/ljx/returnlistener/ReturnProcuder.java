package com.ljx.returnlistener;

import com.ljx.exchange.quickstart.MyConsumer;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lenovo on 2019/12/26.
 */
public class ReturnProcuder {


    private static final Logger log = LoggerFactory.getLogger(ReturnProcuder.class);

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

        String exchange = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        String msg = "Hello RabbitMQ Send return message!";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText,
                                     String exchange,
                                     String routingkey,
                                     AMQP.BasicProperties basicProperties,
                                     byte[] body) throws IOException {
                log.info("-------------handle  return--------------------");
                log.info("replyCode: " + replyCode);
                log.info("replyText: " + replyText);
                log.info("routingkey: " + routingkey);
                log.info("BasicProperties: " + basicProperties.toString());
                log.info("body: " + new String(body));
            }
        });
        //channel.basicPublish(exchange, routingKey,true,null,msg.getBytes());
        channel.basicPublish(exchange, routingKeyError,true,null,msg.getBytes());
    }
}
