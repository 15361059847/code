package com.imooc.miaosha.rabbitmq.controller;

import com.imooc.miaosha.rabbitmq.MQSender;
import com.imooc.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2019/11/22.
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    MQSender mqSender;

   /* @RequestMapping("/demo1")
    public Result<String> mq1(){
        mqSender.send("hello word");
        return Result.success("");
    }


    @RequestMapping("/demo2")
    public Result<String> mq2(){
        mqSender.sendTopic("hello word");
        return Result.success("");
    }

    @RequestMapping("/demo3")
    public Result<String> mq3(){
        mqSender.sendFanout("hello word");
        return Result.success("");
    }

    @RequestMapping("/demo4")
    public Result<String> mq4(){
        mqSender.sendHeader("hello word");
        return Result.success("");
    }*/

}
