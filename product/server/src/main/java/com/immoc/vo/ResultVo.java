package com.immoc.vo;

import lombok.Data;

/**
 * Created by lenovo on 2019/8/15.
 */
@Data
public class ResultVo<T> {
    private String code;
    private String msg;
    private T data;
}
