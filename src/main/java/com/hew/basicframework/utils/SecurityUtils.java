package com.hew.basicframework.utils;

import com.hew.basicframework.DO.UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author HeXiaoWei
 * @date 2020/11/26 14:21
 */
public class SecurityUtils {
    /**
     * 获取用户信息
     *
     * @return UserInfo
     */
    public static UserInfo getUser() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取权限
     *
     * @return
     */
    public static Collection<SimpleGrantedAuthority> getAuthorities() {
        return (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
