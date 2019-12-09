package com.imooc.miaosha.good.model;

import lombok.Data;

/**
 * Created by lenovo on 2019/10/31.
 */
@Data
public class Goods {

    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

}
