package com.imooc.miaosha.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lenovo on 2019/10/21.
 */
@Service
public class RedisPoolFactory {

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig config= new JedisPoolConfig();
        JedisPool jedisPool = new  JedisPool(redisConfig.getHost(),redisConfig.getPort());
        return jedisPool;
    }
}
