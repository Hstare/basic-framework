package com.hew.basicframework.config.security;

import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.enums.CommonEnum;
import com.hew.basicframework.service.LoginService;
import com.hew.basicframework.utils.HttpUtils;
import com.hew.basicframework.utils.JWTUtils;
import com.hew.basicframework.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 14:33
 */
@Component
public class LoginUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailsService.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = loginService.getUserInfo(username);
        LOGGER.info("UserDetails:{}", userInfo);
        if (null == userInfo) {
            throw new UsernameNotFoundException("不存在此用户！");
        }
        String encodePassword = passwordEncoder.encode(userInfo.getPassword());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userInfo.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority)));
        userInfo.setGrantedAuthorities(authorities);
        User user = new User(username, encodePassword, userInfo.isEnabled(), userInfo.isAccountNonExpired(), userInfo.isCredentialsNonExpired(), userInfo.isAccountNonLocked(), authorities);
        String token = JWTUtils.create(userInfo);
        HttpServletResponse response = HttpUtils.getResponse();
        response.setHeader(CommonEnum.TOKEN_PARAMETER.getValue(), token);
        //redis存储
        String key = CommonEnum.REDIS_CACHE_LOGIN_USER.getValue() + userInfo.getUsername();
        RedisUtils.set(key, token, 3, TimeUnit.HOURS);
        return user;
    }
}
