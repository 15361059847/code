package com.imooc.miaosha.util;

import java.util.UUID;

/**
 * Created by lenovo on 2019/10/25.
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
