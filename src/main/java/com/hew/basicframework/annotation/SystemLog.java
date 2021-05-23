package com.hew.basicframework.annotation;

import java.lang.annotation.*;


/**
 * @author HeXiaoWei
 * @date 2020/10/18 12:26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    /*
     * 日志内容
     * */
    String value() default "";

    /*
     * 日志类型(0: 登录[login],1: 退出[logout],2: 操作[operation])
     * */
    int type() default Constant.TYPE_OPERATION;

    /*
     * 操作类型(默认不知道操作类型)
     * */
    int operationType() default 0;

}
