package com.crimson.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogHandler {
    private final Logger logger = Logger.getLogger(getClass());

    @Before("execution(* com.crimson.*.*.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature() + " - " + joinPoint.getSignature().getName() + ": started it's work");
    }

    @After("execution(* com.crimson.*.*.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature() + " - " + joinPoint.getSignature().getName() + ": finished it's work");
    }

    @AfterReturning(
            pointcut = "execution(* com.crimson.*.*.*.*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (result == null) result = true;
        logger.info(joinPoint.getSignature() + " - " + joinPoint.getSignature().getName() + ": returned value of " + result);
    }

    @AfterThrowing(
            pointcut = "execution(* com.crimson.*.*.*.*(..))",
            throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error(joinPoint.getSignature() + " - " + joinPoint.getSignature().getName() + ": returned exception!", error);
    }
}