package com.hew.basicframework.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author HeXiaoWei
 * @date 2020/10/15 14:16
 */
public class HttpUtils {

    public static void responseJson(HttpServletResponse response,String json) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            writer.write(json);
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }


    /**
     * 获取response
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
       return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取request
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
       return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取用户信息
     * @return UserInfo
     */
    public static User getUser() {
       return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
