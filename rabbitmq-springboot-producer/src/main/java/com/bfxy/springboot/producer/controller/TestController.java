package com.bfxy.springboot.producer.controller;

import com.bfxy.springboot.entity.Order;
import com.bfxy.springboot.producer.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2020/1/21.
 */
@RestController
public class TestController {

    @Autowired
    private RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @RequestMapping("/test1")
    public void test1() throws Exception {
        Map<String,Object> properties = new HashMap<>();
        properties.put("number","12345");
        properties.put("send_time",simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello RabbitMQ For SpringBoot",properties);
    }

    @RequestMapping("/test2")
    public void test2() throws Exception {
        Order order = new Order("001","第一个订单");
        rabbitSender.sendOrder(order);
    }
}
