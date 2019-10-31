package com.imooc.miaosha.order.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/10/31.
 */
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
