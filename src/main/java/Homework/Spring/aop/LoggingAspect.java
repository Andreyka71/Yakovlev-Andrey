package Homework.Spring.aop;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* Homework.Spring.controller..*(..))")
    public void logMethodName(JoinPoint joinPoint) {
        System.out.println("Вызвался метод: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* Homework.Spring.controller..*(..))")
    public Object ExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        Object proceed = joinPoint.proceed();
        Instant end = Instant.now();

        long executionTime = Duration.between(start, end).toMillis();
        System.out.println("Время выполнения: " + executionTime + "ms");
        return proceed;
    }
}
