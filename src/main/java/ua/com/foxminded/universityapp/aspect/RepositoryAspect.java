package ua.com.foxminded.universityapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryAspect extends LoggingAspect {
    @Pointcut("execution(public * ua.com.foxminded.universityapp.model.ClassRepository.*(..))")
    public void callClassRepository() {}

    @Pointcut("execution(public * ua.com.foxminded.universityapp.model.CourseRepository.*(..))")
    public void callCourseRepository() {}

    @Pointcut("execution(public * ua.com.foxminded.universityapp.model.GroupRepository.*(..))")
    public void callGroupRepository() {}

    @Pointcut("execution(public * ua.com.foxminded.universityapp.model.UserRepository.*(..))")
    public void callUserRepository() {}

    @Before("callClassRepository() || callCourseRepository() || callGroupRepository() || callUserRepository()")
    public void beforeCallRepository(JoinPoint jp) {
        before(jp);
    }

    @After("callClassRepository() || callCourseRepository() || callGroupRepository() || callUserRepository()")
    public void afterCallRepository(JoinPoint jp) {
        after(jp);
    }
}
