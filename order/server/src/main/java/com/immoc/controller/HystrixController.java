package com.immoc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by lenovo on 2019/9/29.
 */

@RestController
public class HystrixController {

    @GetMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8082/product/listForOrder", Arrays.asList("2019081301"),String.class);
    }

}
