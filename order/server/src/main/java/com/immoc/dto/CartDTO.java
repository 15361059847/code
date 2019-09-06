package com.immoc.dto;

import lombok.Data;

/**
 * Created by lenovo on 2019/8/23.
 */
@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
