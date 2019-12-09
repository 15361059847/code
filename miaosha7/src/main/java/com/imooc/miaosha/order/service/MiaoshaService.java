package com.imooc.miaosha.order.service;

import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.vo.GoodsVo;

import java.awt.image.BufferedImage;

/**
 * Created by lenovo on 2019/11/1.
 */
public interface MiaoshaService {
    OrderInfo miaosha(MiaoShaUser user, GoodsVo goods);

    long getMiaoshaResult(Long id, long goodsId);

    boolean checkPath(MiaoShaUser user, long goodsId, String path);

    String createMiaoshaPath(MiaoShaUser user, long goodsId);

    BufferedImage createVerifyCode(MiaoShaUser user, long goodsId);

    boolean checkVerifyCode(MiaoShaUser user, long goodsId, int verifyCode);
}
