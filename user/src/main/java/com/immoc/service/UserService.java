package com.immoc.service;

import com.immoc.dataobject.UserInfo;

/**
 * Created by lenovo on 2019/9/23.
 */
public interface UserService {
    UserInfo findByOpenid(String openid);
}
