package com.immoc.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientController {


    @GetMapping("/getOrderMsg")
    public String getMsg() {
        //1、第一种方式（直接使用restTemplate，url写死）
        RestTemplate restTemplate = new RestTemplate();
         String response = restTemplate.getForObject("http://localhost:8081/msg", String.class);
        log.info("response={}" + response);
        return "获取" + response;
    }

}
