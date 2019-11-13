package com.imooc.miaosha.user.dao;

import com.imooc.miaosha.user.model.MiaoShaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by lenovo on 2019/10/24.
 */
@Mapper
public interface MiaoShaUserDao {
    @Select("select * from miaosha_user where id = #{id}")
    MiaoShaUser getById(@Param("id") long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    void update(MiaoShaUser updateUser);
}
