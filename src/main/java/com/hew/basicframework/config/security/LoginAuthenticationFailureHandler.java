package com.hew.basicframework.config.security;

import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 17:19
 */
@Component
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOGGER.error("认证登录失败处理:{}", exception.getMessage());
        ResultVo<Object> result = new ResultVo<>();
        HttpUtils.responseJson(response, result.fail("登录失败"));
    }
}
