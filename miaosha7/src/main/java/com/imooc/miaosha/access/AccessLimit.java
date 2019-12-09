package com.imooc.miaosha.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by lenovo on 2019/12/6.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
     int seconds();
     int maxCount();
     boolean needLine() default true;

}
