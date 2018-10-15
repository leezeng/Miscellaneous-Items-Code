package com.server.asperct;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 每次controller请求进来，都记录日志信息
 *
 * @author CYX
 * @create 2018-06-10-12:22
 */
@Aspect
public class MyAsperct {

    /**
     * 前置通知
     */
    @Before("execution(* com.sf.controller..*.*(..))")
    public void before() {
        System.out.println("前置通知....");
    }

    /**
     * 后置通知
     * returnVal,切点方法执行后的返回值
     */
    @AfterReturning(value = "execution(* com.sf.controller..*.*(..))")
    public void AfterReturning() {
        System.out.println("后置通知....");
    }

}
