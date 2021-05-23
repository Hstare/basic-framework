package com.hew.basicframework.config.security;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.enums.CodeMessageEnum;
import com.hew.basicframework.enums.CommonEnum;
import com.hew.basicframework.exception.JWTAuthenticationException;
import com.hew.basicframework.utils.HttpUtils;
import com.hew.basicframework.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * JWTAuthenticationFilter 表明已经登录这个过滤器只验证token是否合法
 *
 * @author HeXiaoWei
 * @date 2020/10/13 15:30
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private String authorizationParameter = CommonEnum.TOKEN_PARAMETER.getValue();
    private List<String> permitAll = Arrays.asList("/login", "/favicon.ico");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        UserInfo user = null;
        String token = request.getHeader(authorizationParameter);
        try {
            String uri = request.getRequestURI();
            LOGGER.info("uri-->:{}", uri);
            String s = Optional.ofNullable(uri).orElse("");
            boolean notPermitUrl = !(s.startsWith("/swagger-ui/") || s.startsWith("/swagger-resources") || s.startsWith("/v3/") || permitAll.contains(uri));
            if (notPermitUrl) {
                if (StringUtils.isEmpty(token)) {
                    throw new JWTAuthenticationException("Token 不能为空");
                }
                DecodedJWT jwt = JWTUtils.verify(token);
                user = JSONObject.parseObject(jwt.getSubject(), UserInfo.class);
                Collection<SimpleGrantedAuthority> authorities = user.getGrantedAuthorities();
                JWTAuthenticationToken authentication = new JWTAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            //token 过期之后刷新token
            String newToken = JWTUtils.refreshToken(user, token);
            if (newToken == null) {
                LOGGER.error("JWT 认证Token过期异常:{}", e.getMessage());
                ResultVo<Object> result = new ResultVo<>();
                HttpUtils.responseJson(response, result.fail(CodeMessageEnum.TOKEN_EXPIRED.getCode(), e.getMessage()));
            }
        } catch (JWTVerificationException e) {
            LOGGER.error("JWT 认证验证异常:{}", e.getMessage());
            ResultVo<Object> result = new ResultVo<>();
            HttpUtils.responseJson(response, result.fail(e.getMessage()));
        } catch (JWTAuthenticationException e) {
            LOGGER.error("JWT 认证统一异常:{}", e.getMessage());
            ResultVo<Object> result = new ResultVo<>();
            HttpUtils.responseJson(response, result.fail(e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("JWT 认证过滤异常:{}", e.getMessage());
            ResultVo<Object> result = new ResultVo<>();
            HttpUtils.responseJson(response, result.fail(e.getMessage()));
        }
    }
}
