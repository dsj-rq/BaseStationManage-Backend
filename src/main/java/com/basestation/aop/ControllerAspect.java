package com.basestation.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Created by me on 2020/4/15.
 */
@Aspect
@Configuration
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(public * com.basestation.contoller..*.*(..)))")
    public void controllerLog(){

    }

    @Around("controllerLog()")
    public Object controllerLogAround(ProceedingJoinPoint joinPoint){
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> targetClass = method.getDeclaringClass();
            String target = targetClass.getName() + "#" + method.getName();
            String params = JSON.toJSONString(joinPoint.getArgs());
            logger.info("开始执行{} 参数:{}",  target, params);
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long timeConsuming = System.currentTimeMillis() - start;
            logger.info("接口{}运行耗时{}ms",target,timeConsuming);
            return result;
        } catch (Throwable throwable) {
            logger.error("服务异常",throwable);
         }
        return null;
    }

}
