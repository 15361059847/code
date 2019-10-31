package com.imooc.miaosha.user.dao;

import com.imooc.miaosha.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/*
* @Repository需要在Spring中配置扫描地址，然后生成Dao层的Bean才能被注入到Service层中。
* @Mapper不需要配置扫描地址，通过xml里面的namespace里面的接口地址，生成了Bean后注入到Service层中。
* */
@Mapper
public interface UserDao {
    User findUserById(Integer id);
}
