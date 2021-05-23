package com.hew.basicframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HeXiaoWei
 * @date 2021/5/23 19:48
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Activation {
    /**
     * 通过指定key查找实现
     */
    String[] value() default {};

    /**
     * 默认升序
     */
    int order() default Integer.MAX_VALUE;

    /*
    * 是否包含扩展点组件
    * */
    boolean defaultValue() default false;
}
