package com.crimson.core.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;

@Aspect
@Component
public class LogHandler{
    private final Logger logger = Logger.getLogger(GLOBAL_LOGGER_NAME);

    @Before("execution(* com.crimson.core.*.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + " / " + joinPoint.getSignature().getName() + ": started it's work");
    }

    @After("execution(* com.crimson.core.*.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + " / " + joinPoint.getSignature().getName() + ": finished it's work");
    }

    @AfterReturning(
            pointcut = "execution(* com.crimson.core.*.*.*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + " / " + joinPoint.getSignature().getName() + ": returned value of object: " + result);
    }

    @AfterThrowing(
            pointcut = "execution(* com.crimson.core.*.*.*(..))",
            throwing= "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error(joinPoint.getSignature().getDeclaringTypeName() + " / " + joinPoint.getSignature().getName() + ": returned exception!", error);
    }
}