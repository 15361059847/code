package com.imooc.miaosha.redis.service;




/**
 * Created by lenovo on 2019/10/21.
 */
public interface RedisService {

     <T> T get(KeyPrefix prefix,String key ,Class<T> clazz);
     <T> boolean set(KeyPrefix prefix,String key ,T value);
     <T> String beanToString(T value) ;
     <T> T stringToBean(String str, Class<T> clazz);
     <T> boolean exists(KeyPrefix prefix,String key);
     <T> Long incr(KeyPrefix prefix, String key);
     <T> Long decr(KeyPrefix prefix, String key);


}
