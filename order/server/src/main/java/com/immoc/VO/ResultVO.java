package com.immoc.VO;

import lombok.Data;

/**
 * Created by lenovo on 2019/8/20.
 */
@Data
public class ResultVO<T>{
    private Integer code;
    private String msg;
    private T data;

}
