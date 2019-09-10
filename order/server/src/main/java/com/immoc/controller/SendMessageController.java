package com.immoc.controller;


import com.immoc.dto.OrderDTO;
import com.immoc.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by lenovo on 2019/9/9.
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

//    @GetMapping("/sendMessage")
//    public void process(){
//        String message = "date" + new Date();
//        streamClient.input1().send(
//                MessageBuilder.withPayload(message).build()
//        );
//    }


    @GetMapping("/sendMessage")
    public void process(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.input1().send(
                MessageBuilder.withPayload(orderDTO).build()
        );
    }
}
