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
    STATUS_NORMAL(0,"normal","正常状态"),
    STATUS_DISABLE(-1,"disable","禁用状态"),

    DELETED_NOT(0,"notDeleted","未删除"),
    DELETED(1,"deleted","已删除"),

    TYPE_LOGIN(0,"login","登录"),
    TYPE_LOGOUT(1,"logout","退出"),
    TYPE_OPERATION(2,"operation","操作"),

    TOKEN_PARAMETER(0,"Authorization","Token参数名"),

    REDIS_CACHE_LOGIN_USER(0,"sys:login:user:","Redis缓存登录用户前缀"),

    OPERATION_COUNT(0,"count","统计"),
    OPERATION_QUERY(1,"get","查询"),
    OPERATION_MULTIPLE_QUERY(1,"list","查询多条值"),
    OPERATION_SAVE(2,"save","插入"),
    OPERATION_INSERT(2,"insert","插入"),
    OPERATION_UPDATE(3,"update","更新"),
    OPERATION_REMOVE(4,"remove","删除"),
    OPERATION_DELETE(4,"delete","删除"),
    OPERATION_IMPORT(5,"import","导入"),
    OPERATION_EXPORT(6,"export","导出");

    private int code;
    private String value;
    private String msg;
}
