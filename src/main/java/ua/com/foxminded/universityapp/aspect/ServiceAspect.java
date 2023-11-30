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
    @Pointcut("execution(public * ua.com.foxminded.universityapp.service.ClassService.*(..))")
    public void callClassService() {}

    @Pointcut("execution(public * ua.com.foxminded.universityapp.service.CourseService.*(..))")
    public void callCourseService() {}

    @Pointcut("execution(public * ua.com.foxminded.universityapp.service.GroupService.*(..))")
    public void callGroupService() {}

    @Pointcut("execution(public * ua.com.foxminded.universityapp.service.UserService.*(..))")
    public void callUserService() {}

    @Before("callClassService() || callCourseService() || callGroupService() || callUserService()")
    public void beforeCallRepository(JoinPoint jp) {
        before(jp);
    }

    @After("callClassService() || callCourseService() || callGroupService() || callUserService()")
    public void afterCallRepository(JoinPoint jp) {
        after(jp);
    }
}
