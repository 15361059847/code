package com.imooc.miaosha.user.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lenovo on 2019/10/24.
 */
@Data
public class MiaoShaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastloginDate;
    private Integer logincount;
}
