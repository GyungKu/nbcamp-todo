package com.sparta.nbcamptodo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "걸린 시간 기록")
public class TimeAop {

    @Around("execution(* com.sparta.nbcamptodo.controller..*(..))")
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("method = {}, 걸린 시간 = {}ms", joinPoint.getSignature().getName(), System.currentTimeMillis() - start);
        return result;
    }
}
