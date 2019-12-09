package com.imooc.miaosha.redis.service.impl;



/**
 * Created by lenovo on 2019/12/6.
 */
public class AccessKey extends BasePrefix {

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey accecc  = new AccessKey(5, "access");
}
