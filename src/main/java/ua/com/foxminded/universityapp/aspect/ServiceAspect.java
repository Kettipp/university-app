package ua.com.foxminded.universityapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect extends LoggingAspect {

    @Before("execution(public * ua.com.foxminded.universityapp.service..*(..))")
    public void beforeCallRepository(JoinPoint jp) {
        before(jp);
    }

    @After("execution(public * ua.com.foxminded.universityapp.service..*(..))")
    public void afterCallRepository(JoinPoint jp) {
        after(jp);
    }
}
