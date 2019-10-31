package com.imooc.miaosha.redis.service;

/**
 * Created by lenovo on 2019/10/21.
 */
public interface KeyPrefix {

    int expireSeconds();
    String getPrefix();
}
