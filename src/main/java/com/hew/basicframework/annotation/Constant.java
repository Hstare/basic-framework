package com.hew.basicframework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 17:48
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Constant {
    int TYPE_LOGIN = 0;
    int TYPE_LOGOUT = 1;
    int TYPE_OPERATION = 2;
}
