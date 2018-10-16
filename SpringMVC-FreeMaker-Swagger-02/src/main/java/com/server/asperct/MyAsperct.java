package com.server.asperct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 每次controller请求进来，都记录日志信息
 *
 * @author CYX
 * @create 2018-06-10-12:22
 */
@Aspect
public class MyAsperct {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAsperct.class);

    /**
     * 前置通知
     */
    @Before("execution(* com.server.controller..*.*(..))")
    public void before(JoinPoint joinPoint) {
        LOGGER.info("前置通知....");
    }

    /**
     * 后置通知
     * returnVal,切点方法执行后的返回值
     */
    @AfterReturning(value = "execution(* com.server.controller..*.*(..))")
    public void AfterReturning(JoinPoint joinPoint) {
        LOGGER.info("后置通知....");
    }

    /**
     * 环绕拦截
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* com.server.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        Map<String, Object> outputParamMap = new HashMap<String, Object>();
        // result的值就是被拦截方法的返回值
        Object result = proceedingJoinPoint.proceed();

        //可以从request中获取入参信息
        //do something....

        outputParamMap.put("userNumber", "");
        outputParamMap.put("resultParam", result);

        LOGGER.info("环绕拦截");

        //日志输出
        return result;
    }

}
