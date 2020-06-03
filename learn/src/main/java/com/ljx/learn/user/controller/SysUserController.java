package com.ljx.learn.user.controller;

import com.ljx.learn.user.model.SysUser;
import com.ljx.learn.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/getUser")
    public SysUser getUser(@RequestParam Integer userid){
       SysUser user =  sysUserService.getUser(userid);
       return user;
    }
}
