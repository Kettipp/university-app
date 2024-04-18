package ua.com.foxminded.universityapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryAspect extends LoggingAspect {
    @Before("execution(public * ua.com.foxminded.universityapp.model.repository..*(..))")
    public void beforeCallRepository(JoinPoint jp) {
        before(jp);
    }

    @After("execution(public * ua.com.foxminded.universityapp.model.repository..*(..))")
    public void afterCallRepository(JoinPoint jp) {
        after(jp);
    }
}
