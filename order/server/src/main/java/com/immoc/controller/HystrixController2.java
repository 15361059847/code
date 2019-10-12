package com.immoc.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by lenovo on 2019/9/29.
 */

@RestController
public class HystrixController2 {


    @HystrixCommand(fallbackMethod = "test")
    @GetMapping("/getProductInfoList2")
    public String getProductInfoList2(@RequestParam("number") Integer number){
        if(number%2 != 0) {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject("http://localhost:8082/product/listForOrder", Arrays.asList("2019081301"), String.class);
        }
        return "成功";
    }

    private String test(@RequestParam("number") Integer number){
        return "请稍后再试";
    }



}
