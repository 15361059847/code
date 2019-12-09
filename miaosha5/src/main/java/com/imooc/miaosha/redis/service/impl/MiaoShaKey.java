package com.imooc.miaosha.redis.service.impl;

/**
 * Created by lenovo on 2019/11/28.
 */
public class MiaoShaKey extends BasePrefix{

    private MiaoShaKey(String prefix) {
        super(prefix);
    }
    public static MiaoShaKey idGoodsOver = new MiaoShaKey("go");

}
