package com.ljx.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {

    private static final Logger log = LoggerFactory.getLogger(MyConsumer.class);

    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag,  //消费者标签
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        log.info("消费端启动成功：---------------------------------");
        log.info("body: " + new String(body));

        try {
            Thread.sleep(3000);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }

        if((Integer) properties.getHeaders().get("num") == 0){
            channel.basicNack(envelope.getDeliveryTag(),false,true);
        }else{
            channel.basicAck(envelope.getDeliveryTag(),false);
        }


    }
}