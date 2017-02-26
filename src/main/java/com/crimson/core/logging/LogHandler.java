package com.crimson.core.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class LogHandler{
    private Logger logger = Logger.getLogger(getClass());

    @Before("execution(* *.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + ": started it's work");
    }

    @After("execution(* *.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + ": finished it's work");
    }

    @AfterReturning(
            pointcut = "execution(* *.*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info(joinPoint.getSignature().getName() + ": returned value of object: " + result);
    }

    @AfterThrowing(
            pointcut = "execution(* *.*(..))",
            throwing= "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error(joinPoint.getSignature().getName() + ": returned exception!", error);
    }
}