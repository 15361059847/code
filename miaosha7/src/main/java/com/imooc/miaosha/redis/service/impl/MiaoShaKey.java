package com.imooc.miaosha.redis.service.impl;

/**
 * Created by lenovo on 2019/11/28.
 */
public class MiaoShaKey extends BasePrefix{

    private MiaoShaKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static MiaoShaKey idGoodsOver = new MiaoShaKey(0,"go");
    public static MiaoShaKey getMiaoShaPath = new MiaoShaKey(60,"mp");
    public static MiaoShaKey getMiaoshaVerifyCode = new MiaoShaKey(300, "vc");

}
