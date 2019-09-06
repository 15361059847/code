package com.immoc.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by lenovo on 2019/8/30.
 */
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {



    @Value("${env}")
    private String order;

    @GetMapping("/print")
    public String print(){
        return  order;
    }


}
