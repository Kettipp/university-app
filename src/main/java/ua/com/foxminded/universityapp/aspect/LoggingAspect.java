package ua.com.foxminded.universityapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void before(JoinPoint jp) {
        if(logger.isTraceEnabled()) {
            String methodName = jp.getSignature().getName();
            String className = jp.getTarget().getClass().getName();
            logger.trace("Class : {} method : {}", className, methodName);
        }
    }

    public void after(JoinPoint jp) {
        if(logger.isTraceEnabled()) {
            String methodName = jp.getSignature().getName();
            String className = jp.getTarget().getClass().getName();
            logger.trace("Class : {} method : {} successful", className, methodName);
        }
    }
}
