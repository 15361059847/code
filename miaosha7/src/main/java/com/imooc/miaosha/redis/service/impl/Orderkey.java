package com.imooc.miaosha.redis.service.impl;

/**
 * Created by lenovo on 2019/10/21.
 */
public class Orderkey extends BasePrefix{

    public Orderkey( String prefix) {
        super( prefix);
    }

    public static Orderkey getMiaoShaOrderByUidgID = new Orderkey("moug");
}
