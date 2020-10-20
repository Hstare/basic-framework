package com.hew.basicframework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author HeXiaoWei
 * @date 2020/9/7 10:03
 */
@AllArgsConstructor
@Getter
public enum CodeMessageEnum {
    SUCCESS(0,"成功"),
    FAIL(-1,"失败"),

    TOKEN(100,"非法Token"),
    TOKEN_EXPIRED(101,"Token已过期，请重新获取");
    private int code;
    private String msg;
}
