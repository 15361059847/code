package com.imooc.miaosha.redis.service.impl;

/**
 * Created by lenovo on 2019/10/21.
 */
public class UserKey extends BasePrefix{


    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
