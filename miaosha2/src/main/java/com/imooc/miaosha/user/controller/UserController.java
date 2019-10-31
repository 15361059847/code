package com.imooc.miaosha.user.controller;


import com.imooc.miaosha.redis.service.impl.RedisServiceImpl;
import com.imooc.miaosha.user.model.User;
import com.imooc.miaosha.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @Autowired(required = false)
    private RedisServiceImpl redisService;

    @RequestMapping("/findUserById")
    public User findUserById() {
        Integer id = 1;
        User user = userService.findUserById(id);
        return user;
    }



}
