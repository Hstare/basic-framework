package com.hew.basicframework.annotation.aspect;

import com.hew.basicframework.DO.SystemLogInfo;
import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.annotation.Constant;
import com.hew.basicframework.annotation.SystemLog;
import com.hew.basicframework.enums.CommonEnum;
import com.hew.basicframework.service.SystemLogService;
import com.hew.basicframework.utils.HttpUtils;
import com.hew.basicframework.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 14:37
 */
@Aspect
@Component
public class SystemLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class);
    @Autowired
    SystemLogService systemLogService;

    @Pointcut("@annotation(com.hew.basicframework.annotation.SystemLog)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = Instant.now().toEpochMilli();
        Object proceed = point.proceed();
        long endTime = Instant.now().toEpochMilli();
        //保存日志
        saveSysLog(point, endTime - startTime);
        return proceed;
    }

    public void saveSysLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        String className = point.getTarget().getClass().getName();
        Method method = signature.getMethod();
        String methodName = method.getName();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        SystemLogInfo log = new SystemLogInfo();

        log.setLogContent(systemLog.value());
        log.setLogType(systemLog.type());
        log.setCostTime(time);
//        log.setCreateBy()
        log.setIp(IpUtils.getIpAddr());
        String requestMethod = className + "." + methodName + "()";
        log.setMethod(requestMethod);
        log.setRequestParam("");
//        log.setRequestType()
        log.setRequestUrl(HttpUtils.getURL());
        log.setCreateTime(new Date());
        UserInfo user = HttpUtils.getUser();
        log.setUsername(user.getUsername());
        Integer operateType = getOperateType(methodName, systemLog.type());
        /*if(operateType == -1) {
            LOGGER.warn("{} 方法名不符合规范",requestMethod);
        }*/
        log.setOperateType(operateType);
        systemLogService.save(log);
    }

    private Integer getOperateType(String methodName,Integer operationType) {
        if(operationType != 0) {
            return operationType;
        }
        if(startWith(methodName,CommonEnum.OPERATION_LOGIN.getValue())){
            return CommonEnum.OPERATION_LOGIN.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_COUNT.getValue())) {
            return CommonEnum.OPERATION_COUNT.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_QUERY.getValue(),CommonEnum.OPERATION_MULTIPLE_QUERY.getValue())) {
            return CommonEnum.OPERATION_QUERY.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_INSERT.getValue(),CommonEnum.OPERATION_SAVE.getValue())) {
            return CommonEnum.OPERATION_INSERT.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_UPDATE.getValue())) {
            return CommonEnum.OPERATION_UPDATE.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_DELETE.getValue(),CommonEnum.OPERATION_REMOVE.getValue())) {
            return CommonEnum.OPERATION_DELETE.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_IMPORT.getValue())) {
            return CommonEnum.OPERATION_IMPORT.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_EXPORT.getValue())) {
            return CommonEnum.OPERATION_EXPORT.getCode();
        } else if(startWith(methodName,CommonEnum.OPERATION_LOGOUT.getValue())) {
            return CommonEnum.OPERATION_LOGOUT.getCode();
        }
        return -1;
    }

    private boolean startWith(String methodName, String... start){
        List<String> strings = Arrays.asList(start);
        return strings.stream().filter(s -> methodName.startsWith(s)).collect(Collectors.toList()).size() > 0;
    }

}
