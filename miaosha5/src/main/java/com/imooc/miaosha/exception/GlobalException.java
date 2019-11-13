package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import lombok.Data;

/**
 * Created by lenovo on 2019/10/25.
 */
@Data
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }
}
