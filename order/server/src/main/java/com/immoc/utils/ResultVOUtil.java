package com.immoc.utils;

import com.immoc.VO.ResultVO;

/**
 * Created by lenovo on 2019/8/20.
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
