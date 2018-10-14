package com.server.spring.spring08;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author CYX
 * @create 2018-04-30-22:18
 */
public class TestSpringApp08 {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("/spring/test08/aop-applicationContext08.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring08/aop-applicationContext08.xml");

        HelloWorldImpl1 helloWorldImpl1 = context.getBean("helloWorldImpl1", HelloWorldImpl1.class);
        HelloWorldImpl2 helloWorldImpl2 = context.getBean("helloWorldImpl2", HelloWorldImpl2.class);

        System.out.println();
        helloWorldImpl1.printHelloWorld();
        helloWorldImpl1.doPrint();

        System.out.println();
        helloWorldImpl2.printHelloWorld();
        helloWorldImpl2.doPrint();

    }

}
