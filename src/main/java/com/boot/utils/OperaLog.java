package com.boot.utils;

import java.lang.annotation.*;

/**
 * @author lm
 * @version 1.8
 * @date 2021/8/17 19:37
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperaLog {

    /**模块*/
    String module() default "";

    /**描述*/
    String description() default "";

}
