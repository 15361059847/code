package com.imooc.miaosha.rabbitmq;


import com.imooc.miaosha.rabbitmq.model.MiaoshaMessage;
import com.imooc.miaosha.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {

	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	private RedisService redisService;


	public void sendMiaoshaMessage(MiaoshaMessage mm ) {
		String msg = redisService.beanToString(mm);
		log.info("send:" + msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
	}

/*
	public void send(Object message){
		String msg = redisService.beanToString(message);
		log.info("send:" + msg);
		amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
	}

	public void sendTopic(Object message){
		String msg = redisService.beanToString(message);
		log.info("send Topic ：" + msg);
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg +"1");
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2",msg +"2");
	}

	public void sendFanout(Object message){
		String msg = redisService.beanToString(message);
		log.info("send Fanout ：" + msg);
		//第二个参数会被忽略
		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"AA",msg +"1");
	}

	public void sendHeader(Object message){
		String msg = redisService.beanToString(message);
		log.info("send Fanout ：" + msg);
		MessageProperties properties = new MessageProperties();
		properties.setHeader("header1","value1");
		properties.setHeader("header2","value2");
	    Message obj = new Message(msg.getBytes(),properties) ;
		amqpTemplate.convertAndSend(MQConfig.HEADER_EXCHANGE,"",obj);
	}
*/


}
