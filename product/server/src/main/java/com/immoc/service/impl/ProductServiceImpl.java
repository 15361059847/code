package com.immoc.service.impl;


import com.immoc.dataobject.ProductInfo;

import com.immoc.dto.CartDTO;
import com.immoc.enums.ProductStatusEnum;
import com.immoc.enums.ResultEnum;
import com.immoc.exception.ProductException;
import com.immoc.repository.ProductInfoRepository;
import com.immoc.service.ProductService;
import com.immoc.utils.JsonUtil;
import com.imooc.product.common.DecreaseStockInput;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * Created by lenovo on 2019/8/14.
 */
@Service
public class  ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findProductInfoByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<String> productIdList){
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            Optional<ProductInfo> productInfoOptional =  productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if(!productInfoOptional.isPresent()){
                throw  new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存是否足够
            ProductInfo productInfo =  productInfoOptional.get();
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw  new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);

            //发送mq消息
            amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfo));
        }
    }
}
