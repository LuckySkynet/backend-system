package com.backend.aop;

import com.alibaba.fastjson.JSON;
import com.backend.annotation.SysLog;
import com.backend.entity.SysLogEntity;
import com.backend.service.SysLogService;
import com.backend.utils.HttpContextUtils;
import com.backend.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 *
 * @author Skynet
 * @date 2017年04月24日 14:49
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.backend.annotation.SysLog)")
    public void logPointCut(){ }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLogEntity = new SysLogEntity();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            //注解上的描述
            sysLogEntity.setOperation(sysLog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        sysLogEntity.setParams(params);

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLogEntity.setIp(IPUtils.getIpAddr(request));

        //TODO 用户名，通过Shiro完成用户登录功能
        sysLogEntity.setUsername("skynet");
        //保存系统日志
        sysLogService.save(sysLogEntity);
    }

}
