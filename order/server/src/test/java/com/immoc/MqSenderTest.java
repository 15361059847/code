package com.immoc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 测试mq
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqSenderTest {


	@Autowired
	private AmqpTemplate amqpTemplate;

	@Test
	public void send() {
		amqpTemplate.convertAndSend("myQueue","当前时间:" + new Date() );
	}

	@Test
	public void sendOrder() {
		amqpTemplate.convertAndSend("myOrder","computer","当前时间:" + new Date() );
	}

}
