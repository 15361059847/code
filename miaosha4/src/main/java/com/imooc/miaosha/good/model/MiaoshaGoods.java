package com.imooc.miaosha.good.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/10/31.
 */
@Data
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
