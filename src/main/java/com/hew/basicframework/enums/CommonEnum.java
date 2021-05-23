package com.hew.basicframework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 12:29
 */
@AllArgsConstructor
@Getter
public enum CommonEnum {
    STATUS_NORMAL(0, "normal", "正常状态"),
    STATUS_DISABLE(-1, "disable", "禁用状态"),

    DELETED_NOT(0, "notDeleted", "未删除"),
    DELETED(1, "deleted", "已删除"),

    TYPE_LOGIN(0, "login", "登录"),
    TYPE_LOGOUT(1, "logout", "退出"),
    TYPE_OPERATION(2, "operation", "操作"),

    TOKEN_PARAMETER(0, "Authorization", "Token参数名"),

    REDIS_CACHE_LOGIN_USER(0, "sys:login:user:", "Redis缓存登录用户前缀"),
    REDIS_CACHE_USER_TOKEN(0, "network:user:token:", "Redis缓存登录用户Token"),
    REDIS_CACHE_SMS_CODE(0, "network:sms:code:", "Redis缓存短信验证码"),

    OPERATION_DEFAULT(0, "default", "默认操作"),
    OPERATION_LOGIN(1, "login", "登录"),
    OPERATION_COUNT(2, "count", "统计"),
    OPERATION_QUERY(3, "get", "查询"),
    OPERATION_MULTIPLE_QUERY(3, "list", "查询多条值"),
    OPERATION_PAGE_QUERY(3, "page", "分页查询"),
    OPERATION_SAVE(4, "save", "插入"),
    OPERATION_INSERT(4, "insert", "插入"),
    OPERATION_UPDATE(5, "update", "更新"),
    OPERATION_REMOVE(6, "remove", "删除"),
    OPERATION_DELETE(6, "delete", "删除"),
    OPERATION_IMPORT(7, "import", "导入"),
    OPERATION_EXPORT(8, "export", "导出"),
    OPERATION_LOGOUT(-1, "logout", "退出");

    private int code;
    private String value;
    private String msg;
}
