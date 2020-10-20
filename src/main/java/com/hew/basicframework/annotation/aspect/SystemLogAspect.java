package com.hew.basicframework.annotation.aspect;

import com.hew.basicframework.DO.SystemLogInfo;
import com.hew.basicframework.DO.UserInfo;
import com.hew.basicframework.annotation.Constant;
import com.hew.basicframework.annotation.SystemLog;
import com.hew.basicframework.service.SystemLogService;
import com.hew.basicframework.utils.HttpUtils;
import com.hew.basicframework.utils.IpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 14:37
 */
@Aspect
@Component
public class SystemLogAspect {
    @Autowired
    SystemLogService systemLogService;

    @Pointcut("@annotation(com.hew.basicframework.annotation.SystemLog)")
    public void pointcut(){}

    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long time = System.currentTimeMillis() - startTime;
        //保存日志
        saveSysLog(point, time);
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
        log.setMethod(className + "." + methodName + "()");
        log.setRequestParam("");
//        log.setRequestType()
//        log.setRequestUrl()
        log.setCreateTime(new Date());
        User user = HttpUtils.getUser();
        log.setUsername(user.getUsername());
        systemLogService.save(log);
    }

}
