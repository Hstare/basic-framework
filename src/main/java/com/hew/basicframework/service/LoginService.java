package com.hew.basicframework.service;

import com.hew.basicframework.DO.UserInfo;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 14:36
 */
public interface LoginService {
    UserInfo getUserInfo(String username);
}
