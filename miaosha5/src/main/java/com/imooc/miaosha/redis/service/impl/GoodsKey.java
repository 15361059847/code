package com.imooc.miaosha.redis.service.impl;

/**
 * Created by lenovo on 2019/10/21.
 */
public class GoodsKey extends BasePrefix{


    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");

}
