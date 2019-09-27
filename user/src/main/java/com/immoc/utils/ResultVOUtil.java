package com.immoc.utils;


import com.immoc.enums.ResultEnum;
import com.immoc.vo.ResultVo;

/**
 * Created by 廖师兄
 * 2017-12-09 22:53
 */
public class ResultVOUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setCode("0");
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVo success() {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode("0");
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVo error(ResultEnum resultEnum) {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(resultEnum.getCode().toString());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }
}
