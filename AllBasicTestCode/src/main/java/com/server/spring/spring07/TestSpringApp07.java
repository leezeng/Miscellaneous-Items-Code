package com.server.spring.spring07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author CYX
 * @create 2018-04-30-21:36
 */
public class TestSpringApp07 {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("./conf/spring/spring07/applicationContext07.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring07/applicationContext07.xml");


        HelloWorld helloWorld = context.getBean("helloWorld", HelloWorld.class);
        System.out.println(helloWorld);
        helloWorld.setMessage("123");
        helloWorld.getMessage();

        HelloWorld helloWorld2 = context.getBean("helloWorld", HelloWorld.class);
        helloWorld2.getMessage();
        System.out.println(helloWorld2);
        helloWorld2.setMessage("456");
        helloWorld2.getMessage();

        System.out.println(helloWorld == helloWorld2);

    }

}
