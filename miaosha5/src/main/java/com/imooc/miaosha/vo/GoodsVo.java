package com.imooc.miaosha.vo;

import com.imooc.miaosha.good.model.Goods;
import lombok.Data;

import java.util.Date;

/**
 * Created by lenovo on 2019/10/31.
 */
@Data
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
