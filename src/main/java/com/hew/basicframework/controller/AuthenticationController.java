package com.hew.basicframework.controller;

import com.alibaba.fastjson.JSON;
import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.annotation.SystemLog;
import com.hew.basicframework.enums.CommonEnum;
import com.hew.basicframework.query.LoginQuery;
import com.hew.basicframework.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 17:55
 */
@RestController
@Api(tags = "认证控制")
public class AuthenticationController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginQuery login){
        UserInfo userInfo = (UserInfo) loginService.getUserInfo(login.getUsername());
        ResultVo<UserInfo> resultVo = new ResultVo<>();
        resultVo.success(null);
        return JSON.toJSONString(resultVo.success(userInfo));
    }

    @GetMapping("/failure")
    public String failure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        ResultVo<UserInfo> resultVo = new ResultVo<>();
        return resultVo.fail(exception.getMessage());
    }
    @GetMapping("/success")
    @ApiOperation("测试成功连接")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public String success(Authentication authentication){
        authentication.getAuthorities().forEach(System.out::println);
        ResultVo<String> resultVo = new ResultVo<>();
        return resultVo.success("请求成功！！！！！！！！！！！！！！");
    }
    @GetMapping("/test")
    @SystemLog(value = "", operationType = 2)
    @PreAuthorize("hasAuthority('admin11')")
    public String test(){
        ResultVo<String> resultVo = new ResultVo<>();
        return resultVo.success("请求成功！test!!!!!!!!!!");
    }
}
