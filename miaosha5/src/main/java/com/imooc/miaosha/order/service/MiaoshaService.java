package com.imooc.miaosha.order.service;

import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.vo.GoodsVo;

/**
 * Created by lenovo on 2019/11/1.
 */
public interface MiaoshaService {
    OrderInfo miaosha(MiaoShaUser user, GoodsVo goods);
}
