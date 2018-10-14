package com.server.spring.spring09;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author CYX
 * @create 2018-05-01-11:25
 */
public class TestSpringApp09 {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("/spring/test09/aop-applicationContext09.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring09/aop-applicationContext09.xml");

        Car car=(Car) context.getBean("car");

        car.go();

    }

}
