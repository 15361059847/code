package com.immoc.service;


import com.immoc.dataobject.ProductInfo;
import com.immoc.dto.CartDTO;

import java.util.List;

/**
 * Created by lenovo on 2019/8/14.
 */
public interface ProductService {
    /**
     *  查询所有在商品列表
     */
    List<ProductInfo>  findUpAll();

    /**
     *  根据商品id集合查询商品集合
     */
    List<ProductInfo>  findByProductIdIn(List<String> productIdList);

    /**
     * 扣商品库存
     */
    void  decreaseStock(List<CartDTO> cartDTOList);
}
