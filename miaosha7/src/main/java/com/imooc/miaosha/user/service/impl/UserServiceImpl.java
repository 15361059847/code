package com.imooc.miaosha.user.service.impl;


import com.imooc.miaosha.user.dao.UserDao;
import com.imooc.miaosha.user.model.User;
import com.imooc.miaosha.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{


    @Autowired(required = false)
    private UserDao userDao;

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }
}
