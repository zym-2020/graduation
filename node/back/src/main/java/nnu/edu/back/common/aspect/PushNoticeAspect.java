package nnu.edu.back.common.aspect;

import nnu.edu.back.service.SSEService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/03/29/20:54
 * @Description:
 */
@Aspect
@Component
public class PushNoticeAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SSEService sseService;

    @Pointcut("@annotation(nnu.edu.back.common.aspect.PushNotice)")
    public void push() {}

    @Around("push()")
    public Object afterPush(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        operate(joinPoint);
        return result;
    }

    private void operate(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PushNotice pushNotice = method.getAnnotation(PushNotice.class);
        String value = pushNotice.value();
        switch (value) {
            case "all":
                Object[] args = joinPoint.getArgs();
                System.out.println(args);
                log.info("123456");
                break;
        }
    }
}
