package com.hew.basicframework.exception;

import com.hew.basicframework.VO.ResultVo;
import com.hew.basicframework.config.security.LoginAuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;

/**
 * @author HeXiaoWei
 * @date 2020/9/7 10:08
 */
@RestControllerAdvice
public class GlobalException {
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlerNoFoundException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String handleDuplicateKeyException(DuplicateKeyException e){
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("数据库中已存在该记录");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String HttpRequestMethodNotSupportedException(Exception e){
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("没有权限，请联系管理员授权");
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("文件大小超出限制, 请压缩或降低文件大小! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("字段太长,超出数据库字段的长度");
    }

    @ExceptionHandler(PoolException.class)
    public String handlePoolException(PoolException e) {
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("Redis 连接异常!");
    }

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e){
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("没有权限，请联系管理员");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAuthenticationException(AccessDeniedException e){
        LOGGER.error(e.getMessage(), e);
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail("不允许访问");
    }


    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e){
        LOGGER.error("全局异常",e);
        e.printStackTrace();
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo.fail(e.getLocalizedMessage());
    }
}

