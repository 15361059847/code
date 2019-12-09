package com.imooc.miaosha.user.service;

import com.imooc.miaosha.login.vo.LoginVo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.user.model.MiaoShaUser;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by lenovo on 2019/10/24.
 */

public interface MiaoShaUserService {


    MiaoShaUser getById(long id);

    boolean login(HttpServletResponse response,LoginVo loginVo);

    MiaoShaUser getByToken(HttpServletResponse response,String token);
}
