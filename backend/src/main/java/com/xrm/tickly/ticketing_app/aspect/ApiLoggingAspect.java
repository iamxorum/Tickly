package com.xrm.tickly.ticketing_app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Around("execution(* com.xrm.tickly.ticketing_app.controller..*(..))")
    public Object logApiExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Entering method: {}", joinPoint.getSignature());
        logger.info("Request parameters: {}", Arrays.toString(joinPoint.getArgs()));

        long start = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("Exception in method: {} with message: {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }

        long executionTime = System.currentTimeMillis() - start;
        logger.info("Exiting method: {} with response: {} in {} ms", joinPoint.getSignature(), result, executionTime);
        return result;
    }
}