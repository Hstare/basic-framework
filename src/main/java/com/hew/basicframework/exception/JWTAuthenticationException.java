package com.hew.basicframework.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 15:57
 */
public class JWTAuthenticationException extends AuthenticationException {
    public JWTAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JWTAuthenticationException(String msg) {
        super(msg);
    }
}
