package com.immoc.utils;

import java.util.Random;

/**
 * Created by lenovo on 2019/8/20.
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式:时间 +随机数
     */
    public static synchronized  String getKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + number.toString();
    }
}
