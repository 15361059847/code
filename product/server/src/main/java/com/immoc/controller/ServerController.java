package com.immoc.controller;


import com.immoc.dataobject.ProductInfo;
import com.immoc.dto.CartDTO;
import com.immoc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lenovo on 2019/8/22.
 */
@RestController
public class ServerController {

    @Autowired
    private ProductService productService;

    @GetMapping("/msg")
    public String msg(){
        return "product msg";
    }

    //GET方法获取商品列表
    @RequestMapping(value = "/listForOrder", method = RequestMethod.GET)
    public List<ProductInfo> listForOrder(@RequestParam("productIdList") List<String> productIdList){
        return productService.findByProductIdIn(productIdList);
    }


    //POST方法获取商品列表
    @PostMapping("/listForOrder1")
    public List<ProductInfo> listForOrder1(@RequestBody List<String> productIdList){
        return productService.findByProductIdIn(productIdList);
    }

//    //POST方法扣库存
//    @PostMapping("/decreaseStock")
//    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList){
//        productService.decreaseStock(decreaseStockInputList);
//    }

    //POST方法扣库存
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }

}
