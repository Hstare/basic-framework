package com.hew.basicframework.service.impl;

import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author HeXiaoWei
 * @date 2020/10/16 11:14
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public UserInfo getUserInfo(String username) {
        UserInfo userInfo = new UserInfo();
        Set<String> strings = new HashSet<>();
        strings.add("admin");
        return userInfo.setUsername(username).setPassword("123456").setAuthorities(strings);
    }
}
