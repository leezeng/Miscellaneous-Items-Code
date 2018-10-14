package com.server.spring.spring10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Aspect aop
 *
 * @author CYX
 * @create 2018-05-03-22:06
 */
public class TestSpringApp10 {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("/spring/test10/aop-applicationContext10.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring10/aop-applicationContext10.xml");


        UserController userController = context.getBean("userController", UserController.class);
        userController.handler();
    }

}
