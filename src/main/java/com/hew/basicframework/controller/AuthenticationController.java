package com.hew.basicframework.controller;

import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.DO.Users;
import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.mapper.UsersMapper;
import com.hew.basicframework.query.LoginQuery;
import com.hew.basicframework.service.LoginService;
import com.hew.basicframework.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.IOException;
import java.util.List;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 17:55
 */
@RestController
@Api(tags = "认证控制")

public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    LoginService loginService;
    @Autowired
    UsersMapper usersMapper;

    @PostMapping("/login")
    public String login(@RequestBody LoginQuery login) {
        UserInfo userInfo = (UserInfo) loginService.getUserInfo(login.getUsername());
        ResultVo<UserInfo> resultVo = new ResultVo<>();
        resultVo.success(null);
        return resultVo.success(userInfo);
    }

    @GetMapping("/failure")
    public String failure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        ResultVo<UserInfo> resultVo = new ResultVo<>();
        return resultVo.fail(exception.getMessage());
    }

    @GetMapping("/success")
    @ApiOperation("测试成功连接")
    @PreAuthorize("hasAuthority('admin')")
    public String success(Authentication authentication) {
        authentication.getAuthorities().forEach(System.out::println);
        ResultVo<String> resultVo = new ResultVo<>();
        return resultVo.success("请求成功！！！！！！！！！！！！！！");
    }

    @GetMapping("/test")
//    @SystemLog(value = "", operationType = 2)
//    @PreAuthorize("hasAuthority('admin11')")
//    @Cacheable(value = "cache",key="#p0",condition = "tt == 0")
    public void test() throws IOException {
        ResultVo<String> resultVo = new ResultVo<>();
        List<Users> users = usersMapper.selectAll();
        ExcelUtils.write("文件写测试", Users.class, "sheet0", users);
//        return resultVo.success("users.toString()");
    }
}
