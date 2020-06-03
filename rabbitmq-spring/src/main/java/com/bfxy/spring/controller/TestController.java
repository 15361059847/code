package com.bfxy.spring.controller;

import com.bfxy.entity.Order;
import com.bfxy.entity.Packaged;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by lenovo on 2020/1/2.
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @RequestMapping("/test1")
    public void test1() throws Exception{
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc","信息描述");
        messageProperties.getHeaders().put("type","自定义消息类型");
        Message message = new Message("Hello Rabbit".getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("--------添加额外设置------------");
                message.getMessageProperties().getHeaders().put("desc","额外信息描述");
                message.getMessageProperties().getHeaders().put("attr","额外新加属性");
                return message;
            }
        });
    }


    @RequestMapping("/test2")
    public void test2() throws Exception{
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message("mq 消息1234".getBytes(),messageProperties);
        rabbitTemplate.send("topic001","spring.abc",message);


        rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello topic001");
        rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello topic002");
    }

    @RequestMapping("/test3")
    public void test3(){
        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));

        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));

        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));

        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));

        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));

        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
                Binding.DestinationType.QUEUE,
                "test.direct", "direct", new HashMap<>()));

        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.topic.queue", false))		//直接创建队列
                        .to(new TopicExchange("test.topic", false, false))	//直接创建交换机 建立关联关系
                        .with("user.#"));	//指定路由Key


        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.fanout.queue", false))
                        .to(new FanoutExchange("test.fanout", false, false)));

        //清空队列数据
        rabbitAdmin.purgeQueue("test.topic.queue", false);
    }


    @RequestMapping("/test4")
    public void test4() throws Exception{
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message("mq 消息123456789".getBytes(),messageProperties);
        rabbitTemplate.send("topic001","spring.abc",message);
        rabbitTemplate.send("topic002","rabbit.abc",message);
    }

    @RequestMapping("/test5")
    public void test5() throws Exception{
        Order order = new Order();
        order.setId("001");
        order.setName("订单消息");
        order.setContent("描述消息");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.out.println("order json" + json);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(),messageProperties);
        rabbitTemplate.send("topic001","spring.order",message);
    }

    @RequestMapping("/test6")
    public void test6() throws Exception{
        Order order = new Order();
        order.setId("001");
        order.setName("订单消息");
        order.setContent("描述消息");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.out.println("order json" + json);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","com.bfxy.entity.Order");

        Message message = new Message(json.getBytes(),messageProperties);
        rabbitTemplate.send("topic001","spring.order",message);
    }

    @RequestMapping("/test7")
    public void test7() throws Exception{
        Order order = new Order();
        order.setId("001");
        order.setName("订单消息");
        order.setContent("描述消息");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.out.println("order json" + json);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","order");

        Message message1 = new Message(json.getBytes(),messageProperties);
        rabbitTemplate.send("topic001","spring.order",message1);


        Packaged packaged = new Packaged();
        packaged.setId("001");
        packaged.setName("包裹消息");
        packaged.setDescription("包裹描述消息");

        String json2 = mapper.writeValueAsString(packaged);
        System.out.println("packaged json" + json2);

        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setContentType("application/json");
        messageProperties2.getHeaders().put("__TypeId__","packaged");

        Message message2 = new Message(json.getBytes(),messageProperties);
        rabbitTemplate.send("topic001","spring.packaged",message2);

    }

    @RequestMapping("/test8")
    public void test8() throws Exception{
/*        byte[] body = Files.readAllBytes(Paths.get("d:/002_books","picture.png"));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("image/png");
        messageProperties.getHeaders().put("extName","png");
        Message message = new Message(body,messageProperties);
        rabbitTemplate.send("","image_queue",message);*/





        byte[] body = Files.readAllBytes(Paths.get("d:/002_books","mysql.pdf"));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/pdf");
        Message message = new Message(body,messageProperties);
        rabbitTemplate.send("","pdf_queue",message);
    }


}
