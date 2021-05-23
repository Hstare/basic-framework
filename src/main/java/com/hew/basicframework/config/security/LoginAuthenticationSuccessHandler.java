package com.hew.basicframework.config.security;

import com.alibaba.fastjson.JSON;
import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.enums.CommonEnum;
import com.hew.basicframework.utils.HttpUtils;
import com.hew.basicframework.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 17:43
 */
@Component
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LOGGER.info("SuccessHandler-->:{}", authentication);
        ResultVo<String> result = new ResultVo<>();
        result.setCode(0);
        result.setMsg("登录成功");
        HttpUtils.responseJson(response, JSON.toJSONString(result));
    }
}
