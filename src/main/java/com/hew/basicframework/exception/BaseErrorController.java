package com.hew.basicframework.exception;

import com.alibaba.fastjson.JSON;
import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.config.security.LoginAuthenticationFailureHandler;
import com.hew.basicframework.enums.CodeMessageEnum;
import io.swagger.annotations.Api;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;

/**
 * @author HeXiaoWei
 * 排除404的错误
 * @date 2020/9/9 17:39
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
@Api(tags = "处理404错误")
public class BaseErrorController extends AbstractErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationFailureHandler.class);


    public BaseErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping
    public String error() {
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("访问资源不存在！");
    }

}
