package com.ljx.learn.user.service.impl;

import com.ljx.learn.user.dao.SysUserDao;
import com.ljx.learn.user.model.SysUser;
import com.ljx.learn.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysUserService")
public class SysUserImpl implements SysUserService {


   @Autowired
    private SysUserDao dao;

    @Override
    public SysUser getUser(Integer userid) {
        return dao.getUser(userid);
    }
}
