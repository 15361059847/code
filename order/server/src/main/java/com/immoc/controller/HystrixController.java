package com.immoc.controller;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by lenovo on 2019/9/29.
 */

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //自定义降级
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求数达到后才计算
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), //休眠时间窗
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//错误率
    })
    @GetMapping("/getProductInfoList1")
    public String getProductInfoList1(@RequestParam("number") Integer number){
        if(number%2 != 0) {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject("http://localhost:8082/product/listForOrder", Arrays.asList("2019081301"), String.class);
        }
        return "成功";
    }

    public String defaultFallback(){
        return "默认提示，请稍后再试啊";
    }
}
