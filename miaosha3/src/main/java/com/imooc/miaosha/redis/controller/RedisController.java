package com.imooc.miaosha.redis.controller;


import com.imooc.miaosha.redis.service.RedisService;
import com.imooc.miaosha.redis.service.impl.UserKey;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {


    @Autowired(required = false)
    private RedisService redisService;

    @RequestMapping("/redisGet")
    public Result<User> redisGet() {
        User user  = redisService.get(UserKey.getById,"1",User.class);
        return Result.success(user);
    }

    @RequestMapping("/redisSet")
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setUsername("11111");
        Boolean b =  redisService.set(UserKey.getById,"1",user);
        return  Result.success(b);
    }

}
