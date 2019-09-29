package com.immoc.controller;

import com.immoc.client.ProductClient;
import com.immoc.dataobject.ProductInfo;
import com.immoc.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2019/8/22.
 */
@RestController
@Slf4j
public class ClientController {

//      @Autowired
//    private LoadBalancerClient loadBalancerClient;

//      @Autowired
//      private RestTemplate restTemplate;
//
    @GetMapping("/getProductMsg")
    public String getMsg() {
        //1、第一种方式（直接使用restTemplate，url写死）
        RestTemplate restTemplate = new RestTemplate();
         String response = restTemplate.getForObject("http://localhost:8082/msg", String.class);
        log.info("response={}" + response);
        return "获取" + response;
    }
        //2、第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用RestTemplate）
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort()) + "/msg";
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(url,String.class);

        //3、第三种方式（利用@LoadBalanced，可在restTemplate里面使用应用的名字）
//        String response = restTemplate.getForObject("http://PRODUCT/msg",String.class);
//
//        log.info("response={}" + response);
//        return "获取" + response;
//    }

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg1")
    public String getMsg1(){
        String response =  productClient.productMsg();
        log.info("response={}" + response);
        return "获取" + response;
    }


    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    @ResponseBody
    public  List<ProductInfo> listForOrder(){
        List<String> productIdList = new ArrayList<>();
        productIdList.add("2019081301");
        productIdList.add("2019081401");
        productIdList.add("2019081402");
        List<ProductInfo> list =  productClient.listForOrder(productIdList);
        log.info("response={}" + list);
        return list;
    }


    @PostMapping("/getOrderList1")
    public  List<ProductInfo> listForOrder1(){
        List<String> productIdList = new ArrayList<>();
        productIdList.add("2019081301");
        productIdList.add("2019081401");
        productIdList.add("2019081402");
        List<ProductInfo> list =  productClient.listForOrder1(productIdList);
        log.info("response={}" + list);
        return list;
    }

    @PostMapping("/getDecreaseStock")
    public String decreaseStock1(){
        List<CartDTO> cartDTOList = new ArrayList<>();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId("2019081301");
        cartDTO.setProductQuantity(2);
        cartDTOList.add(cartDTO);
        productClient.decreaseStock(cartDTOList);
        return "ok";
    }
}
