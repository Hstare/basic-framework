package com.hew.basicframework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.enums.CodeMessageEnum;
import com.hew.basicframework.enums.CommonEnum;
import com.hew.basicframework.exception.JWTAuthenticationException;
import com.sun.javafx.tk.TKClipboard;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 16:06
 */
public class JWTUtils {
    private static final String SECRET = "!@#)(**%BONC@COM.CN#^&(!%&@*^@";
    private static final String ISSUER = "bonc.com.cn";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).build();
    private static final Date EXPIRE_HALF_HOURS = Date.from(LocalDateTime.now().plus(30, ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()).toInstant());

    public static String create(UserInfo user) {
        user.setPassword(null);
        String token;
        Date EXPIRE_HALF_HOURS = Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC));
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withExpiresAt(EXPIRE_HALF_HOURS)
                    .withSubject(JSONObject.toJSONString(user))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTAuthenticationException("JWT创建异常：无效的签名设置");
        }
        return token;
    }

    public static DecodedJWT verify(String token) {
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException(CodeMessageEnum.TOKEN_EXPIRED.getMsg());
        } catch (JWTVerificationException exception) {
            throw new JWTAuthenticationException("JWT验证异常");
        }
        return jwt;
    }

    public static String refreshToken(UserInfo user, String oldToken) {
        if (user == null) {
            return null;
        }

        HttpServletResponse response = HttpUtils.getResponse();
        String key = CommonEnum.REDIS_CACHE_LOGIN_USER.getValue() + user.getUsername();
        String o = (String) RedisUtils.get(key);
        if (o != null && o.equals(oldToken)) {
            String token = JWTUtils.create(user);
            response.setHeader(CommonEnum.TOKEN_PARAMETER.getValue(), token);

            //删除原来已经存在的key
            RedisUtils.del(key);
            RedisUtils.set(key, token, 3, TimeUnit.HOURS);
            return token;
        }
        return null;
    }
}
