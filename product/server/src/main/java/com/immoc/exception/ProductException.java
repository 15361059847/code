package com.immoc.exception;

import com.immoc.enums.ResultEnum;

/**
 * Created by lenovo on 2019/8/26.
 */
public class ProductException  extends RuntimeException{

    private Integer code;

    public ProductException(String message, Integer code) {
        super(message);
        this.code = code;
    }


    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


}
