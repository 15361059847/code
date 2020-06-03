package com.ljx.learn.user.model;


import lombok.Data;

@Data
public class SysUser {
    private Integer userid;
    private String usercode;
    private String password;
    private Integer usertype;
    private Integer status;
}
