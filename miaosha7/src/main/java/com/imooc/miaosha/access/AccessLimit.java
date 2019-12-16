package com.imooc.miaosha.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义标签
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
     int seconds();
     int maxCount();
     boolean needLogin() default true;

}
