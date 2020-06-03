package com.ljx.learn.user.dao;

import com.ljx.learn.user.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface SysUserDao {
    SysUser getUser(Integer userid);
}
