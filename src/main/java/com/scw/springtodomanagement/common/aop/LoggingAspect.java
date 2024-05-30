package com.scw.springtodomanagement.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.scw.springtodomanagement.domain.service..*(..))",
            throwing = "ex")
    public void logError(Exception ex) {
        log.error("예외 발생 AOP {}", ex.getMessage());
    }


}