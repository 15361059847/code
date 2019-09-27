package com.immoc.enums;

import lombok.Getter;

/**
 * Created by lenovo on 2019/8/26.
 */
@Getter
public enum ResultEnum {
    LOGIN_FAIL(1,"登录失败"),
    LOGIN_ERROR(2,"角色权限不对")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
