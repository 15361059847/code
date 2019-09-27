package com.immoc.service.impl;

import com.immoc.dataobject.UserInfo;
import com.immoc.repository.UserInfoRepository;
import com.immoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2019/9/23.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public  UserInfo findByOpenid(String openid){
       return  userInfoRepository.findByOpenid(openid);
    }

}
