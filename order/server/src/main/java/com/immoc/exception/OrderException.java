package com.immoc.exception;

import com.immoc.enums.ResultEnum;

/**
 * Created by lenovo on 2019/8/20.
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
