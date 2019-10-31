package com.imooc.miaosha.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by lenovo on 2019/10/23.
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPsaa){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPsaa +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String input,String saltDB){
        String formPass = inputPassToFormPass(input);
        String DBPass = formPassToDBPass(formPass,saltDB);
        return DBPass;
    }

    public static void main(String[] args){
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"),"1a2b3c4d"));
    }
}
