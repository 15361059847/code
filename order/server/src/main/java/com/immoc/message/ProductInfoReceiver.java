package com.immoc.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.immoc.utils.JsonUtil;
import com.imooc.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lenovo on 2019/9/17.
 */
//@component （把普通pojo实例化到spring容器中，相当于配置文件中的
@Component
@Slf4j
public class ProductInfoReceiver {

   private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        //message --->ProductInfoOutput
       List<ProductInfoOutput> productInfoOutputlist = ( List<ProductInfoOutput>) JsonUtil.fromJson(message,
               new TypeReference< List<ProductInfoOutput>>(){});
        log.info("name : " + productInfoOutputlist.size());

        //存储到redis
        for(ProductInfoOutput productInfoOutput: productInfoOutputlist){
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }
    }
}
