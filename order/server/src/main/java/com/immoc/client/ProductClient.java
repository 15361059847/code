package com.immoc.client;

import com.immoc.dataobject.ProductInfo;
import com.immoc.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lenovo on 2019/8/22.
 */
@FeignClient(name= "product")
public interface ProductClient {

    @GetMapping("/msg")
     String productMsg();


    @RequestMapping(value = "/listForOrder", method = RequestMethod.GET)
    List<ProductInfo> listForOrder(@RequestParam("productIdList") List<String> productIdList);

    @PostMapping("/listForOrder1")
    List<ProductInfo> listForOrder1(@RequestBody List<String> productIdList);

    @PostMapping("/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}
