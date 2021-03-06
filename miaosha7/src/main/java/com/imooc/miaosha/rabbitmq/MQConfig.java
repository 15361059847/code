package com.imooc.miaosha.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {


	public static final String MIAOSHA_QUEUE = "miaosha.queue";
	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String HEADER_QUEUE = "headerQueue";
	public static final String TOPIC_EXCHANGE = "topicExchage";
	public static final String FANOUT_EXCHANGE = "fanoutExchage";
	public static final String HEADER_EXCHANGE = "headerExchage";

	/**
	 * Direct模式 交换机Exchange
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE);
	}

	/**
	 * Topic模式 交换机Exchange
	 * */
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1);
	}
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2);
	}
	@Bean
	public TopicExchange topicExchage(){
		return new TopicExchange(TOPIC_EXCHANGE);
	}
	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with("topic.key1");
	}
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with("topic.#");
	}


	/**
	 * Fanout模式 交换机Exchange
	 * */
	@Bean
	public FanoutExchange fanoutExchage(){
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	@Bean
	public Binding FanoutBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
	}
	@Bean
	public Binding FanoutBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
	}


	/**
	 * Header模式 交换机Exchange
	 * */
	@Bean
	public HeadersExchange headersExchange(){
		return new HeadersExchange(HEADER_EXCHANGE);
	}

	@Bean
	public Queue headersQueue() {
		return new Queue(HEADER_QUEUE);
	}

	@Bean
	public Binding hearderBinding() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("header1","value1");
		map.put("header2","value2");
		return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
	}



	@Bean
	public Queue miaoShaQueue() {
		return new Queue(MIAOSHA_QUEUE);
	}
}
