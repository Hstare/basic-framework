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

    int OPERATION_COUNT = 0;
    int OPERATION_QUERY = 1;
    int OPERATION_MULTIPLE_QUERY = 1;
    int OPERATION_SAVE = 2;
    int OPERATION_INSERT = 2;
    int OPERATION_UPDATE = 3;
    int OPERATION_REMOVE = 4;
    int OPERATION_DELETE = 4;
    int OPERATION_IMPORT = 5;
    int OPERATION_EXPORT = 6;
}
