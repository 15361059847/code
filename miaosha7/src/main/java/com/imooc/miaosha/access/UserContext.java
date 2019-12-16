package com.imooc.miaosha.access;

import com.imooc.miaosha.user.model.MiaoShaUser;

/**
 * Created by lenovo on 2019/12/9.
 */
public class UserContext {


    private static ThreadLocal<MiaoShaUser> userHolder = new  ThreadLocal<MiaoShaUser>();

    public static void setUser(MiaoShaUser user) {
        userHolder.set(user);
    }

    public static MiaoShaUser getUser() {
        return userHolder.get();
    }


}
