package com.imooc.miaosha.redis.service.impl;

/**
 * Created by lenovo on 2019/10/21.
 */
public class MiaoshaUserKey extends BasePrefix{

    private static final  int TOKEN_EXPIRE = 3600 * 24 * 2;

    public MiaoshaUserKey(int expireSeconds,String  prefix) {
        super(expireSeconds,prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(TOKEN_EXPIRE,"id");

}
